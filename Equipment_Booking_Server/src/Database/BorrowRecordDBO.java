/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

/**
 *
 * @author romel
 */

import ModelClasses.BorrowRecord;
import java.util.List;
import java.util.Optional;

public interface BorrowRecordDBO {
    
    Optional<BorrowRecord> findById(String recordId);
    
    /** Records whose actualReturnTime is still null. */
    List<BorrowRecord> findOpen();
    
    /** Insert or update. */
    void save(BorrowRecord record);
}
