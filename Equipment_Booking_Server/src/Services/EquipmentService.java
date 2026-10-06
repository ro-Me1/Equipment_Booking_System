/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

/**
 *
 * @author romel
 */
import Database.EquipmentDBO;
import Database.UserDBO;
import Enums.EquipmentStatus;
import Enums.Permission;
import Exceptions.BusinessRulesException;
import Exceptions.EntityNotFoundException;
import Exceptions.ServiceException;
import ModelClasses.User;
import ModelClasses.Equipment;
import java.util.ArrayList;
import java.util.List;

public class EquipmentService {
    
    private final EquipmentDBO equipmentDBO;
    private final UserDBO userDBO;
    private final PermissionService permissionService;
    
    public EquipmentService(EquipmentDBO equipmentDBO, UserDBO userDBO, PermissionService permissionService){
        this.equipmentDBO = equipmentDBO;
        this.userDBO = userDBO;
        this.permissionService = permissionService;
    }
    
    /** Rules 1-3: the item must be AVAILABLE and the user must hold the item's required permission. */
    public boolean isAvailableForUser(Equipment equipment, User user) {
        return equipment.getCurrentStatus() == EquipmentStatus.AVAILABLE && permissionService.hasPermission(user, equipment.getRequiredPermissionToBorrow());
    }
    
      /**
     * The ONLY place where a status changes. Illegal moves are refused using the table in
     * EquipmentStatus. Internal: the RMI layer must never expose this directly.
     */
    public void changeStatus(Equipment equipment, EquipmentStatus newStatus) throws BusinessRulesException {
        EquipmentStatus current = equipment.getCurrentStatus();
        
        if (newStatus == null || !current.canTransitionTo(newStatus)) {
            throw new BusinessRulesException("Equipment cannot change from " + current + " to " + newStatus);
        }
        
        equipment.setCurrentStatus(newStatus);
        equipmentDBO.save(equipment);
    }
    
    //Operations with an actor
    public Equipment registerEquipment(String actorId, Equipment equipment) throws ServiceException {
    
        permissionService.requirePermission(loadUser(actorId), Permission.MANAGE_EQUIPMENT);
        
        if (equipment == null) {
            throw new BusinessRulesException("equipment must not be null");
        }
        requireText(equipment.getEquipmentId(), "equipmentId");
        requireText(equipment.getName(), "name");
        
        if (equipmentDBO.findById(equipment.getEquipmentId()).isPresent()) {
            throw new BusinessRulesException("equipmentId already exists: " +equipment.getEquipmentId());
        }
        equipment.setCurrentStatus(EquipmentStatus.AVAILABLE); //new items always start AVAILABLE
        
        if (equipment.getRequiredPermissionToBorrow() ==  null) {
            equipment.setRequiredPermissionToBorrow(Permission.BOOK_EQUIPMENT);
        }
        equipmentDBO.save(equipment);
        return equipment;
    }
    
    /** Everything this user could borrow right now. */
    public List<Equipment> listAvailableFor(String actorId) throws ServiceException {
    
        User actor = loadUser(actorId);
        List<Equipment> result = new ArrayList<>();
        for (Equipment e: equipmentDBO.findByStatus(EquipmentStatus.AVAILABLE)) {
            if(isAvailableForUser(e, actor)) {
                result.add(e);
            }
        }
        return result;
    }

    public void sendToMaintenance(String actorId, String equipmentId) throws ServiceException {
    
        permissionService.requirePermission(loadUser(actorId), Permission.OVERRIDE_STATUS);
        changeStatus(loadEquipment(equipmentId), EquipmentStatus.UNDER_MAINTENANCE);   
    }

    public void returnFromMaintenance(String actorId, String equipmentId) throws ServiceException {
    
        permissionService.requirePermisssion(loadUser(actorId), Permission.OVERRIDE_STATUS);
        Equipment equipment = loadEquipment(equipmentId);
        
        if(equipment.getCurrentStatus() != EquipmentStatus.UNDER_MAINTENANCE) {
            throw new BusinessRulesException("Equipment is not under maintenance");
        }
        changeStatus(equipment, EquipmentStatus.AVAILABLE);
    }
    
    public void retire(String actorId, String equipmentId) throws ServiceException {
    
        permissionService.requirePermission(loadUser(actorId), Permission.OVERRIDE_STATUS);
        changeStatus(loadEquipment(equipmentId), EquipmentStatus.RETIRED);
    
    }
    
    //Helpers
    private User loadUser(String userId) throws EntityNotFoundException {
        return userDBO.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
    }
    
    private Equipment loadEquipment(String equipmentId) throws EntityNotFoundException {
        return equipmentDBO.findById(equipmentId).orElseThrow(() -> new EntityNotFoundException("Equipment not found: " +equipmentId));
    }
    
    private static void requireText(String value, String name) throws BusinessRulesException {
        
        if (value == null || value.trim().isEmpty()) {
            throw new BusinessRulesException(name + " must not be blank");
        }       
    }





}
