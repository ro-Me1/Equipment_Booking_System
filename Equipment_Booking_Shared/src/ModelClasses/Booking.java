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
import java.time.LocalDateTime;
import Enums.BookingStatus;

public class Booking implements Serializable {
    
    private String bookId;
    private User user;
    private Equipment equipment;
    private LocalDateTime bookingDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BookingStatus bookingStatus;
    
    public Booking() {}

    public Booking(String bookId, User user, Equipment equipment, LocalDateTime bookingDate, LocalDateTime startTime, LocalDateTime endTime, BookingStatus bookingStatus) {
        this.bookId = bookId;
        this.user = user;
        this.equipment = equipment;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookingStatus = bookingStatus;
    }
        
    /* 
    // Core Business Logic Method called by your Unit Tests
        public boolean isValidTimeframe() {
            if (startTime == null || endTime == null) {
                return false;
            }
            return endTime.isAfter(startTime);
        }   
    */
    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
    
    
}
