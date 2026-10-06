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
import Enums.EquipmentStatus;
import Enums.Role;
import Enums.Permission;
import java.util.List;

public class Equipment implements Serializable {
   private String equipmentId;
   private String name;
   private String category;
   private String description;
   private EquipmentStatus currentStatus;
   private List<Role> allowedRoles; //tracks which roles can access this asset
   private String location;
   private Permission requiredPermissionToBorrow = Permission.BOOK_EQUIPMENT;
   
   public Equipment() {}

    public Equipment(String EquipmentId, String name, String category, String description, EquipmentStatus currentStatus, List<Role> allowedRoles, String location) {
        this.equipmentId = EquipmentId;
        this.name = name;
        this.category = category;
        this.description = description;
        this.currentStatus = currentStatus;
        this.allowedRoles = allowedRoles;
        this.location = location;
    }

   /*
    // Core Business Logic Method called by your Unit Tests
    public boolean isAvailableForUser(User user) {
        if (currentStatus != EquipmentStatus.AVAILABLE) {
            return false;
        }
    
        return user.hasPermission(this.requiredPermissionToBorrow);
    }
*/
    
   
    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String EquipmentId) {
        this.equipmentId = EquipmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EquipmentStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(EquipmentStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public List<Role> getAllowedRoles() {
        return allowedRoles;
    }

    public void setAllowedRoles(List<Role> allowedRoles) {
        this.allowedRoles = allowedRoles;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    public Permission getRequiredPermissionToBorrow(){
        return requiredPermissionToBorrow;
    }
    
    public void setRequiredPermissionToBorrow(Permission p) {
        this.requiredPermissionToBorrow = p;
    }
    
    @Override
    public boolean equals(Object o) {
    if(this == o) return true;
    if (!(o instanceof Equipment)) return false;
    Equipment other = (Equipment) o;
    return equipmentId != null && equipmentId.equals(other.equipmentId);
    }
    
    @Override
    public int hashCode() {
    return equipmentId == null ? 0 : equipmentId.hashCode();
    }
    
    @Override
    public String toString() {
        return "Equipment{" +equipmentId+ ", " +currentStatus+ "}";
    }
   
   
}
