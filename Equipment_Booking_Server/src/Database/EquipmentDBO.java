/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

/**
 *
 * @author romel
 */

import Enums.EquipmentStatus;
import ModelClasses.Equipment;
import java.util.List;
import java.util.Optional;

public interface EquipmentDBO {
    
    Optional<Equipment> findById(String equipmentId);
    
    List<Equipment> findByStatus(EquipmentStatus status);
    
    
    /** Insert or update. */
    void save(Equipment equipment);
}
