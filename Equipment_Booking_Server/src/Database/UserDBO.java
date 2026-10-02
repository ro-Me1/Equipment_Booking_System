/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

/**
 *
 * @author romel
 */

import Enums.Role;
import ModelClasses.User;
import java.util.Optional;

/** What the services need from storage. The PostgreSQL class implements this later. */
public interface UserDBO {
    
    Optional<User> findById(String userId);
    Optional<User> findByEmail(String email);
    
    /** Insert or Update */
    void save(User user);
    
    long countActiveByRole(Role role);
    
}
