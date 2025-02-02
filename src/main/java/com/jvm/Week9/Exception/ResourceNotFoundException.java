package com.jvm.Week9.Exception;

public class ResourceNotFoundException extends RuntimeException {

    private int resourceId;
    private String resourceName;


    // Constructor with message and resourceId (employee ID in this case)
    public ResourceNotFoundException(String message, int resourceId) {
        super(message);
        this.resourceId = resourceId;
    }
    // Constructor with message and resourceId (employee ID in this case)
    public ResourceNotFoundException(String message, String resourceName) {
        super(message);
        this.resourceName = resourceName;
    }
    // Default constructor
    public ResourceNotFoundException(String resourceName) {
        super("Resource not found");
        this.resourceName = resourceName;
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
            return "Resource not found with ID: " + resourceId;
        }
        return super.getMessage();
    }
}
