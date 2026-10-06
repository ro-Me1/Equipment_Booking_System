/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelClasses;

/**
 *
 * @author romel
 */
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import Enums.Permission;


public class BorrowRecord implements Serializable {
    
    private String recordId;
    private Booking booking; //Optional link: can be null for walk-ins
    private User borrower;
    private Equipment equipment;
    private User issuedBy; //Must be evaluated as Staff/Admin on the service layer
    private LocalDateTime checkoutTime;
    private LocalDateTime expectedReturnTime;
    private LocalDateTime actualReturnTime; //Remains null until item is physically back
    private String conditionOnOutput;
    private String conditionOnInput;
    
    public BorrowRecord() {}

    public BorrowRecord(String recordId, Booking booking, User borrower, Equipment equipment, User issuedBy, LocalDateTime checkoutTime, LocalDateTime expectedReturnTime, LocalDateTime actualReturnTime, String conditionOnOutput, String conditionOnInput) {
        this.recordId = recordId;
        this.booking = booking;
        this.borrower = borrower;
        this.equipment = equipment;
        this.issuedBy = issuedBy;
        this.checkoutTime = checkoutTime;
        this.expectedReturnTime = expectedReturnTime;
        this.actualReturnTime = actualReturnTime;
        this.conditionOnOutput = conditionOnOutput;
        this.conditionOnInput = conditionOnInput;
    }
    
    /*
    // Core Business Logic Methods called by your Unit Tests
            public boolean isOverdue() {
                if (actualReturnTime != null) {
                    return false; // Already returned safely
                }
                if (expectedReturnTime == null) {
                    return false;
                }
                return LocalDateTime.now().isAfter(expectedReturnTime);
            }

            public Duration calculateLateDuration() {
                if (!isOverdue()) {
                    return Duration.ZERO;
                }
                return Duration.between(expectedReturnTime, LocalDateTime.now());
            }
    
           
    */
    
    /*
    public BorrowRecord processCheckOut(Booking booking, User staffMember) {
    if (!staffMember.hasPermission(Permission.APPROVE_BORROW)) {
        throw new SecurityException("Unauthorized Operator: This account cannot authorize equipment checkouts.");
    }
    
    BorrowRecord record = new BorrowRecord();
    record.setBooking(booking);
    record.setBorrower(booking.getUser());
    record.setEquipment(booking.getEquipment());
    record.setIssuedBy(staffMember); // Verified safe operator
    record.setCheckoutTime(LocalDateTime.now());
    
    return record;
    }

    public String getRecordId() {
        return recordId;
    }
*/

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public User getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(User issuedBy) {
        this.issuedBy = issuedBy;
    }

    public LocalDateTime getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(LocalDateTime checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public LocalDateTime getExpectedReturnTime() {
        return expectedReturnTime;
    }

    public void setExpectedReturnTime(LocalDateTime expectedReturnTime) {
        this.expectedReturnTime = expectedReturnTime;
    }

    public LocalDateTime getActualReturnTime() {
        return actualReturnTime;
    }

    public void setActualReturnTime(LocalDateTime actualReturnTime) {
        this.actualReturnTime = actualReturnTime;
    }

    public String getConditionOnOutput() {
        return conditionOnOutput;
    }

    public void setConditionOnOutput(String conditionOnOutput) {
        this.conditionOnOutput = conditionOnOutput;
    }

    public String getConditionOnInput() {
        return conditionOnInput;
    }

    public void setConditionOnInput(String conditionOnInput) {
        this.conditionOnInput = conditionOnInput;
    }
    
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof BorrowRecord)) return false;
        BorrowRecord other = (BorrowRecord) o;
        return recordId != null && recordId.equals(other.recordId);
    }
    
    @Override
    public int hashCode() {
        return recordId == null ? 0 : recordId.hashCode();
    }
    
}
