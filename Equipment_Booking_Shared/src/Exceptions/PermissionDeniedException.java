/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 *
 * @author romel
 */
public class PermissionDeniedException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public PermissionDeniedException(String message) {
    super(message);
    }
}
