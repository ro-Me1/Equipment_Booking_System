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
import Database.EquipmentDBO;
import Database.UserDBO;
import Enums.BookingStatus;
import Enums.EquipmentStatus;
import Enums.Permission;
import Exceptions.BusinessRulesException;
import Exceptions.EntityNotFoundException;
import Exceptions.PermissionDeniedException;
import Exceptions.ServiceException;
import ModelClasses.Booking;
import ModelClasses.Equipment;
import ModelClasses.User;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Reservation rules.
 *
 * Life cycle: PENDING -> CONFIRMED -> FULFILLED (done by BorrowService at checkout)
 *            PENDING / CONFIRMED -> CANCELLED
 * Equipment: stays AVAILABLE while a booking is PENDING; becomes BOOKED when CONFIRMED.
 */
public class BookingService {
    private final BookingDBO bookingDBO;
    private final EquipmentDBO equipmentDBO;
    private final UserDBO userDBO;
    private final PermissionService permissionService;
    private final EquipmentService equipmentService;
    private final Clock clock; //Injected so tests can control "now"

    public BookingService(BookingDBO bookingDBO, EquipmentDBO equipmentDBO, UserDBO userDBO, PermissionService permissionService, EquipmentService equipmentService, Clock clock) {
        this.bookingDBO = bookingDBO;
        this.equipmentDBO = equipmentDBO;
        this.userDBO = userDBO;
        this.permissionService = permissionService;
        this.equipmentService = equipmentService;
        this.clock = clock;
    }
    
    /** The actor books for THEMSELVES. */
    public Booking createBooking(String actorId, String equipmentId, LocalDateTime end) throws ServiceException {
    
        User actor = loadUser(actorId);
        permissionService.requirePermission(actor, Permission.BOOK_EQUIPMENT);
        
        LocalDateTime now = now();
        validateTimeFrame(start, end, now);
        
        Equipment equipment = loadEquipment(equipmentId);
        if (!equipmentService.isAvailableForUser(equipment, actor)) {
            throw new BusinessRulesException("Equipment is not available for this user");
        }
        
        for (Booking existing : bookingDBO.findActiveByEquipment(equipmentId)) {
            if(overlaps(existing, start, end)) {
                throw new BusinessRulesException("The equipment is already booked for part of that time");
            }
        }
        
        Booking booking = new Booking(UUID.randomUUID().toString(), actor, equipment, now, start, end, BookingStatus.PENDING);
        bookingDBO.save(booking);
        return booking;
    }
    
    /** Staff/Admin approve a PENDING booking; the equipment becomes BOOKED. */
    public void confirmBooking(String actorId, String bookingId) throws ServiceException {
    
        User actor = loadUser(actorId);
        permissionService.requirePermission(actor, Permission.APPROVE_BORROW);
        
        Booking booking = loadBooking(bookingId);
        if(booking.getBookingStatus() != BookingStatus.PENDING) {
            throw new BusinessRulesException("Only a PENDING booking can be confirmed");
        }
        
        if (!booking.getEndTime().isAfter(now())) {
            throw new BusinessRulesException("This booking already expired");
        }
        
        Equipment equipment = loadEquipment(booking.getEquipment().getEquipmentId());
        if(equipment.getCurrentStatus() != EquipmentStatus.AVAILABLE) {
            throw new BusinessRulesException("Equipment is not available to be reserved");
        }
        
        equipmentService.changeStatus(equipment, EquipmentStatus.BOOKED);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        bookingDBO.save(booking);
    }
    
    /** The owner, or anyone with APPROVE_BORROW, may cancel a PENDING or CONFIRMED booking. */
    public void cancelBooking(String actorId, String bookingId) throws ServiceException {
    
        User actor = loadUser(actorId);
        if(!actor.isActive()) {
            throw new PermissionDeniedException("Account is inactive");
        }
        
        Booking booking = loadBooking(bookingId);
        boolean isOwner = booking.getUser().getUserId().equals(actorId);
        if (!isOwner) {
            permissionService.requirePermission(actor, Permission.APPROVE_BORROW);
        }
        
        BookingStatus status = booking.getBookingStatus();
        if(status != BookingStatus.PENDING && status != BookingStatus.CONFIRMED) {
            throw new BusinessRulesException("Only a PENDING or CONFIRMED booking can be cancelled");
        }
        
        if(status == BookingStatus.CONFIRMED) {
            Equipment equipment = loadEquipment(booking.getEquipment().getEquipmentId());
            equipmentService.changeStatus(equipment, EquipmentStatus.AVAILABLE); //release it
        }
        
        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookingDBO.save(booking);  
    }
    
    //HELPERS
    private LocalDateTime now() {
        return LocalDateTime.now(clock);
    }
    
    private void validateTimeframe(LocalDateTime start, LocalDateTime end, LocalDateTime now) throws BusinessRulesException {
    
        if(start == null || end == null) {
            throw new BusinessRulesException("start and end times are required");
        }
        
        if(!end.isAfter(start)) {
            throw new BusinessRulesException("end time must be after start time");
        }
        
        if(start.isBefore(now)) {
            throw new BusinessRulesException("start time cannot be in the past");
        }
    }
    
    /** Strict comparisons: back-to-back bookings (one ends when the next starts) do not clash. */
    private static boolean overlaps(Booking existing, LocalDateTime start, LocalDateTime end) {       
        return existing.getStartTime().isBefore(end) && start.isBefore(existing.getEndTime());
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
    
    
    
    
    
    
    
    
}
