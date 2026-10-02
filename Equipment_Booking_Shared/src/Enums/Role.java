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
public enum Role {
    ADMINISTRATOR, STAFF, STUDENT;
    
    /**
     * The permissions a role grants by default.
     * A NEW set is returned on every call, so callers can never
     * accidentally modify the defaults.
     */
    
    public EnumSet<Permission> defaultPermissions() {
    
        switch(this) {
            case ADMINISTRATOR:
                return EnumSet.allOf(Permission.class);
            
            case STAFF:
                return EnumSet.of(Permission.BOOK_EQUIPMENT, Permission.APPROVE_BORROW);
            
            case STUDENT:
                return EnumSet.of(Permission.BOOK_EQUIPMENT);
                
            default:
                throw new IllegalStateException("No default permissions defined for " + this);
        
        }
    }
}
