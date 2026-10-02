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
import Enums.Role;
import Enums.Permission;
import java.util.EnumSet;

public class User implements Serializable {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;  //use transient data type transient: never serialized so it never sends over RMI.
    private Role role;
    private boolean active;
    private EnumSet<Permission> grantedPermissions = EnumSet.noneOf(Permission.class);
    private EnumSet<Permission> revokedPermissions = EnumSet.noneOf(Permission.class);
    
    public User() {}
    
    public User (String userId, String firstName, String lastName, String email, String passwordHash, Role role, boolean isActive, EnumSet<Permission> userPermission){
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.passwordHash = passwordHash;
    this.role = role;
    this.active = isActive;
    
    }


   
    
    public String getFullName() {
     return firstName + " " + lastName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    
    public void setRole(Role role) {
        this.role = role;
    }
    
    public boolean isActive() {
        return active;
    }

    public void setIsActive(boolean isActive) {
        this.active = active;
    }
    
    public EnumSet<Permission> getGrantedPermissions() {
        return grantedPermissions;
    }

    public void setGrantedPermissions(EnumSet<Permission> granted) {
        this.grantedPermissions = (granted == null) ? EnumSet.noneOf(Permission.class) : granted;
    }
    
    public EnumSet<Permission> getRevokedPermissions() {
        return revokedPermissions;
    }
    
    public void setRevokedPermissions(EnumSet<Permission> revoked) {
        this.revokedPermissions = (revoked == null) ? EnumSet.noneOf(Permission.class) : revoked;
    }
    
    @Override
    public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User)) return false;
    User other = (User) o;
    return userId != null && userId.equals(other.userId);
    }
    
    @Override
    public int hashCode() {
    return userId == null ? 0 : userId.hashCode();
    }
    
    @Override
    public String toString() {
    return "User{" +userId+ ", " +getFullName()+ ", " +role+ ", active=" +active+ "}";
    }
}
