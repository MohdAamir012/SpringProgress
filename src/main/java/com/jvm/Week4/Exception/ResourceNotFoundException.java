package com.jvm.Week4.Exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(){}
    public void message(int id){
        System.out.println("Empoyee not found with this employee ID"+id);
    }
}
