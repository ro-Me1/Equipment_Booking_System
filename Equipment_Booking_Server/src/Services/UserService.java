/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

/**
 *
 * @author romel
 */
import Database.UserDBO;
import Enums.Permission;
import Enums.Role;
import Exceptions.BusinessRulesException;
import Exceptions.EntityNotFoundException;
import Exceptions.PermissionDeniedException;
import ModelClasses.User;
import java.util.EnumSet;

 /**
 * User-management rules. Every method takes the ACTOR's id (taken from the
 * session by the RMI layer, never trusted from the client) and, where relevant,
 * the TARGET's id.
 */

public class UserService {
    
    private static final String EMAIL_PATTERN = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
    
    private final UserDBO userDBO;
    private final PermissionService permissionService;
    
    public UserService(UserDBO userDBO, PermissionService permissionService) {
    this.userDBO = userDBO;
    this.permissionService = permissionService;
    }
    
    
    //Operations  (Check the first 3 methids for errors with the set,get permissions
    public User createUser(String actorId, User newUser) throws PermissionDeniedException, BusinessRulesException, EntityNotFoundException {
    
        User actor = requireManager(actorId);
        validate(newUser);
        requireCanAssign(actor, newUser.getRole());
        
        String email = newUser.getEmail().trim().toLowerCase();
        
        if (userDBO.findById(newUser.getUserId()).isPresent()) {
            throw new BusinessRulesException("email already exists: " + newUser.getUserId());      
        }
        
        if (userDBO.findByEmail(email).isPresent()) {
            throw new BusinessRulesException("email already in use: " + email);
        }
        
        
        newUser.setEmail(email);
        newUser.setGrantedPermissions(EnumSet.noneOf(Permission.class)); //No pre-set grants
        newUser.setRevokedPermissions(EnumSet.noneOf(Permission.class));
        userDBO.save(newUser);
        return newUser;
        
    }
    
    /** Rule 3: grants and revocations are deliberately left untouched. */
    public void changeRole(String actorId, String targetId, Role newRole) throws PermissionDeniedException, BusinessRulesException, EntityNotFoundException {
       User actor = requireManager(actorId);
       requireNotSelf(actorId, targetId);
       
       if(newRole == null) {
       throw new BusinessRulesException("role must not be null");
       }
       requireCanAssign(actor, newRole);
       
       User target = loadUser(targetId);
       if (target.getRole() == Role.ADMINISTRATOR && newRole != Role.ADMINISTRATOR) {
           requireNotLastActiveAdmin(target);
       }
       target.setRole(newRole);
       userDBO.save(target);
    }
    
    public void grantPermission(String actorId, String targetId, Permission permission) throws PermissionDeniedException, BusinessRulesException, EntityNotFoundException {
    
        User actor = requireManager(actorId);
        requireNotSelf(actorId, targetId);
        requirePermissionArg(permission);
        permissionService.requirePermission(actor, permission); //cannot grant what you do not hold
        
        User target = loadUser(targetId);
        target.getRevokedPermissions().remove(permission);
        target.getGrantedPermissions().add(permission);
        userDBO.save(target);
    }
    
    public void revokePermission(String actorId, String targetId, Permission permission) throws PermissionDeniedException, BusinessRulesException, EntityNotFoundException {
    
        requireManager(actorId);
        requireNotSelf(actorId, targetId);
        requirePermissionArg(permission);
        
        User target = loadUser(targetId);
        target.getGrantedPermissions().remove(permission);
        target.getRevokedPermissions().add(permission);
        userDBO.save(target);
    }
    
    public void deactivateUser(String actorId, String targetId) throws PermissionDeniedException, BusinessRulesException, EntityNotFoundException {
    
        requireManager(actorId);
        requireNotSelf(actorId, targetId);
        
        User target = loadUser(targetId);
        if (target.getRole() == Role.ADMINISTRATOR) {
            requireNotLastActiveAdmin(target);
        }
        
        target.setIsActive(false);
        userDBO.save(target);
    }
    
    public void activateUser(String actorId, String targetId) throws PermissionDeniedException, BusinessRulesException, EntityNotFoundException {
        
        requireManager(actorId);
        User target = loadUser(targetId);
        target.setIsActive(true);
        userDBO.save(target);
        
    }
    
    //Guards
    private User loadUser(String userId) throws EntityNotFoundException {  
            return userDBO.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
    }
    
     /** Loads the actor and checks MANAGE_USERS (an inactive actor fails automatically). */
    private User requireManager(String actorId) throws EntityNotFoundException, PermissionDeniedException {
    
        User actor = loadUser(actorId);
        permissionService.requirePermission(actor, Permission.MANAGE_USERS);
        return actor;
        
    }
    
    private void requireNotSelf(String actorId, String targetId) throws BusinessRulesException {
    
        if (actorId != null && actorId.equals(targetId)) {
            throw new BusinessRulesException("You cannot perform this action on your own account");
        }
    }
    
    /** Cannot hand out a role holding permissions the actor does not hold. */
    private void requireCanAssign(User actor, Role role) throws PermissionDeniedException {
    
        if(!permissionService.effectivePermissions(actor).containsAll(role.defaultPermissions())) {
        throw new PermissionDeniedException("You cannot assign a role with more permissions than you hold");
        }
    }
    
    /** Only matters if the target is currently active: an inactive admin does not count. */
    private void requireNotLastActiveAdmin(User target) throws BusinessRulesException {
      
        if (target.isActive() && userDBO.countActiveByRole(Role.ADMINISTRATOR) <= 1) {
            throw new BusinessRulesException("The last active administrator cannot be demoted or deactivated");
        }
    }
    
    private void requirePermissionArg(Permission permission) throws BusinessRulesException {
    
        if (permission == null) {
            throw new BusinessRulesException("permission must not be null");
        }
    }
    
    //Validation
    private void validate(User u) throws BusinessRulesException {
    
        if (u == null) {
            throw new BusinessRulesException("user must not be null");
        }
        
        requireText(u.getUserId(), "userId");
        requireText(u.getFirstName(), "firstName");
        requireText(u.getLastName(), "lastName");
        requireText(u.getPasswordHash(), "passwordHash");
        requireText(u.getEmail(), "email");
        
        if(!u.getEmail().trim().matches(EMAIL_PATTERN)) {
            throw new BusinessRulesException("email is not valid: " + u.getEmail());
        }
        
        if (u.getRole() == null) {
            throw new BusinessRulesException("role must not be null");
        }
    }
    
    private static void requireText(String value, String name) throws BusinessRulesException {
    
        if(value == null || value.trim().isEmpty()){
            throw new BusinessRulesException(name + "must not be blank");
        }
    }
    
}
