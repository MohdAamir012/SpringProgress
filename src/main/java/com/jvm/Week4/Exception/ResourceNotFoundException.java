package com.jvm.Week4.Exception;

public class ResourceNotFoundException extends RuntimeException {

    private int resourceId;

    // Constructor with message and resourceId (employee ID in this case)
    public ResourceNotFoundException(String message, int resourceId) {
        super(message);
        this.resourceId = resourceId;
    }

    // Default constructor
    public ResourceNotFoundException() {
        super("Resource not found");
    }

    // Getters and setters for resourceId
    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    // Custom message for the exception
    @Override
    public String getMessage() {
        if (resourceId > 0) {
            return "Employee not found with ID: " + resourceId;
        }
        return super.getMessage();
    }
}
