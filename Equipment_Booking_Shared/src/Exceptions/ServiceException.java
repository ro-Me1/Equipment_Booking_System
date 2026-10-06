/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 *
 * @author romel
 */
public class ServiceException extends Exception {
    
    public ServiceException(String message) {
        super(message);
    }
    
    public class BusinessRulesException extends ServiceException{
        public BusinessRulesException(String message) {
            super(message);
        }
    }
}
