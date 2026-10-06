/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Enums;

/**
 *
 * @author romel
 */

import java.util.EnumSet;

public enum EquipmentStatus {
    AVAILABLE, BOOKED, BORROWED, UNDER_MAINTENANCE, RETIRED;
    
    /** The legal moves out of each status. A new set is returned on every call. */
    public EnumSet<EquipmentStatus> allowedNext() {
        switch(this) {
            case AVAILABLE:
                return EnumSet.of(BOOKED, BORROWED, UNDER_MAINTENANCE, RETIRED);
            
            case BOOKED:
                return EnumSet.of(AVAILABLE, BORROWED);
                
            case BORROWED:
                return EnumSet.of(AVAILABLE);
                
            case UNDER_MAINTENANCE:
                return EnumSet.of(AVAILABLE, RETIRED);
                
            case RETIRED:
                return EnumSet.noneOf(EquipmentStatus.class);
                
            default:
                throw new IllegalStateException("No transition defined for " +this);
           
        }
    }
    
    public boolean canTransitionTo(EquipmentStatus next) {
        return allowedNext().contains(next);
    }
}
