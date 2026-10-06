/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

/**
 *
 * @author romel
 */
import Database.BookingDBO;
import Database.BorrowRecordDBO;
import Database.EquipmentDBO;
import Database.UserDBO;
import Enums.BookingStatus;
import Enums.EquipmentStatus;
import Enums.Permission;
import Exceptions.BusinessRulesException;
import Exceptions.EntityNotFoundException;
import Exceptions.ServiceException;
import ModelClasses.Booking;
import ModelClasses.BorrowRecord;
import ModelClasses.Equipment;
import ModelClasses.User;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The physical handover: checkout (from a booking, or a walk-in), return, and overdue checks.
 * A record is OPEN while actualReturnTime is null and CLOSED once it is set.
 */

public class BorrowService {
    
    private final BorrowRecordDBO borrowRecordDBO;
    private final BookingDBO bookingDBO;
    private final EquipmentDBO equipmentDBO;
    private final UserDBO userDBO;
    private final PermissionService permissionService;
    private final EquipmentService equipmentService;
    private final Clock clock;

    public BorrowService(BorrowRecordDBO borrowRecordDBO, BookingDBO bookingDBO, EquipmentDBO equipmentDBO, UserDBO userDBO, PermissionService permissionService, EquipmentService equipmentService, Clock clock) {
        this.borrowRecordDBO = borrowRecordDBO;
        this.bookingDBO = bookingDBO;
        this.equipmentDBO = equipmentDBO;
        this.userDBO = userDBO;
        this.permissionService = permissionService;
        this.equipmentService = equipmentService;
        this.clock = clock;
    }
    
    //CHECKOUT
    /** Hand over equipment that was booked. Expected return = the booking's end time. */
    public BorrowRecord checkOutBooking(String actorId, String bookingId, String conditionOnOutPut) throws ServiceException {
    
        User actor = loadUser(actorId);
        permissionService.requirePermission(actor, Permission.APPROVE_BORROW);
        
        Booking booking = loadBooking(bookingId);
        if (booking.getBookingStatus() != BookingStatus.CONFIRMED) {
            throw new BusinessRuleException("Only a CONFIRMED booking can be checked out");
        }
        
        LocalDateTime now = now();
        if (!booking.getEndTime().isAfter(now)) {
            throw new BusinessRulesException("This booking has expired");
        }
        
           // Reload from storage so we never act on stale copies stored inside the booking
           User borrower = loadUser(booking.getUser().getUserId());
           requireNotSelf(actor, borrower);
           Equipment equipment = loadEquipment(booking.getEquipment().getEquipmentId());
          
           requireBorrowerAllowed(borrower, equipment);
           if (equipment.getCurrentStatus() != EquipmentStatus.BOOKED) {
               throw new BusinessRulesException("Equipment is not reserved for pickup");
           }
           
           equipmentService.changeStatus(equipment, EquipmentStatus.BORROWED);
           booking.setBookingStatus(BookingStatus.FULFILLED);
           BorrowRecord record = new BorrowRecord(UUID.randomUUID().toString(), booking, borrower, equipment, actor, now, booking.getEndTime(), null, conditionOnOutput, null);
           
           bookingDBO.save(booking);
           borrowRecordDBO.save(record);
           return record;
    }

    /** Hand over equipment with no booking. The due time must be supplied. */
    public BorrowRecord checkOutWalkIn(String actorId, String borrowerId, String equipmentId, LocalDateTime expectedReturnTime, String conditionOnOutput) throws ServiceException {
    
        User actor = loadUser(actorId);
        permissionService.requirePermission(actor, Permission.APPROVE_BORROW);
        
        LocalDateTime now = now();
        if(expectedReturnTime == null || !expectedReturnTime.isAfter(now)) {
        throw new BusinessRulesException("Expected return time must be in the future");
        }
        
        User borrower = loadUser(borrowId);
        requireNotSelf(actor, borrower);
        Equipment equipment = loadEquipment(equipmentId);
        requireBorrowerAllowed(borrower, equipment);
        if(equipment.getCurrentStatus() != EquipmentStatus.AVAILABLE) {
            throw new BusinessRulesException("Equipment is not available");
        }
        
        equipmentService.changeStatus(equipment, EquipmentStatus.BORROWED);
        BorrowRecord record = new BorrowRecord(UUID.randomUUID().toString(), null, borrower, equipment, actor, now, expectedReturnTime, null, conditionOnOutput, null);
        borrowRecordDBO.save(record);
        return record;
    }
    
    
    //RETURN
    public BorrowRecord returnEquipment(String actorId, String recordId, String conditionOnInput) throwa ServiceException {
    
        User actor = loadUser(actorId);
        permissionService.requirePermission(actor, Permission.APPROVE_BORROW);
        
        BorrowRecord record = loadRecord(recordId);
        if(record.getActualReturnTime() != null) {
            throw new BusinessRulesException("This item has already been returned");
        }
        LocalDateTime now = now();
        if(now.isBefore(record.getCheckoutTime())) {
            throw new BusinessRulesException("Return time cannot be before checkout time");
        }
        
        Equipment equipment = loadEquipment(record.getEquipment().getEquipmentId());
        equipmentService.changeStatus(equipment, EquipmentStatus.AVAILABLE);
        record.setActualReturnTime(now);
        record.setConditionOnInput(conditionOnInput);
        borrowRecordDBO.save(record);
        return record;
    }












}


    //OVERDUE
    /** Overdue = not returned, has a due time, and now is past it. */
    public boolean isOverdue(BorrowRecord record) {
        return record.getActualReturnTime() == null && record.getExpectedReturnTime() != null && now().isAfter(record.getExpectedReturnTime());
    }
    
    public Duration calculateLateDuration(BorrowRecord record) {
        if(!isOverdue(record)) {
            return Duration.ZERO;
        }
        return Duration.between(record.getExpectedReturnTime(), now());
    }
    
    public List<BorrowRecord> listOverdue(String actorId) throws ServiceException {
        permissionService.requirePermission(loadUser(actorId), Permission.APPROVE_BORROW);
        List <BorrowRecord> result = new ArrayList<>();
        if (isOverdue(r)) {
            result.add(r);
        }
        return result;
    }
   
    //HELPERS
    
    private LocalDateTime now() {
        return LocalDateTime.now(clock);
    }
    
     /** Policy: nobody issues equipment to themselves. */
    private void requireNotSelf(User actor, User borrower) throws BusinessRulesException {
        if (actor.getUserId().equals(borrower.getUserId())) {
            throw new BusinessRulesException("You cannot issue equipment to yourself");
        }
    }
    
     /** Inactive users hold no permissions, so this also rejects inactive borrowers. */
    private void requireBorrowerAllowed(User borrower, Equipment equipment) throws BusinessRulesException {
        if (!permissionService.hasPermission(borrower, equipment.getRequiredPermissionToBorrow())) {
            throw  new BusinessRulesException("Borrower is inactive or not permitted to borrow this equipment");
        }
    }
    
    private User loadUser(String userId) throws EntityNotFoundException {
        return userDBO.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found: " +userId)); 
    }
    
     private Equipment loadEquipment(String equipmentId) throws EntityNotFoundException {
        return equipmentDBO.findById(equipmentId).orElseThrow(() -> new EntityNotFoundException("Equipment not found: " +equipmentId)); 
    }
     
      private Booking loadBooking(String bookingId) throws EntityNotFoundException {
        return bookingDBO.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("Booking not found: " +bookingId)); 
    }
      
       private BorrowRecord loadRecord(String recordId) throws EntityNotFoundException {
        return borrowRecordDBO.findById(recordId).orElseThrow(() -> new EntityNotFoundException("Borrow record not found: " +recordId)); 
    }
    
    }