/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

/**
 *
 * @author romel
 */

import ModelClasses.Booking;
import java.util.List;
import java.util.Optional;

public interface BookingDBO {
    Optional<Booking> findById(String bookId);
    
    /** Bookings for this equipment whose status is PENDING or CONFIRMED. */
    List<Booking> findActiveByEquipment(String equipmentId);
    
    /** Insert or update. */
    void save(Booking booking);
}
