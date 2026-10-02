/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

/**
 *
 * @author romel
 */

import Enums.Permission;
import Exceptions.PermissionDeniedException;
import ModelClasses.User;
import java.util.EnumSet;

/** Decides what a user is allowed to do. Plain Java: no RMI, no database. */
public class PermissionService {
    /**
     * Rule 1: inactive user -> no permissions.
     * Rule 2: otherwise role defaults + granted - revoked.
     * A new set is returned each time, so callers cannot change the user through it.
     */
    
    public EnumSet<Permission> effectivePermissions(User user) {
    
    if (user == null) {
        throw new IllegalArgumentException("User must not be null");
    }
    
    if (!user.isActive() || user.getRole() == null) {
        return EnumSet.noneOf(Permission.class);
    }
    
    EnumSet<Permission> result = user.getRole().defaultPermissions();
    result.addAll(user.getGrantedPermissions());
    result.removeAll(user.getRevokedPermissions());
    return result;
    
    }

    public boolean hasPermission(User user, Permission permission) {
    
        if (permission == null) {
            throw new IllegalArgumentException("permission must not be null");
        }
        
        return effectivePermissions(user).contains(permission);
    }

    public void requirePermission(User user, Permission permission) throws PermissionDeniedException {
    
        if(!hasPermission(user, permission)) {
            throw new PermissionDeniedException("Missing permission: " + permission);
        }
    }


}
