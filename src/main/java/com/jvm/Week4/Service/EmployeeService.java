package com.jvm.Week4.Service;

import com.jvm.Week4.Entity.Employee;
import com.jvm.Week4.Exception.ResourceNotFoundException;
import com.jvm.Week4.Repo.EmployeeRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;

    public EmployeeService(EmployeeRepo employeeRepo){
        this.employeeRepo=employeeRepo;
    }

    public List<Employee> getAllEmployees(){
        return employeeRepo.findAll();
    }
    // Using custom exception class to throw proper message || Best practice fo rexception handling
    public Employee getEmployeeById(int id){
        return employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: ",id));
    }
    public Employee addEmployee(Employee e){
        return employeeRepo.save(e);
    }

    public Employee updateEmployee(int id, Employee employee) {
            return employeeRepo.update(id,employee);
    }

    // Exception Handling: try-catch-finally, custom exceptions, throws, and throw.
    public void deleteEmployee(int id) {
        try {
            if (employeeRepo.existsById(id)) {
                employeeRepo.deleteById(id);
            } else {
                throw new ResourceNotFoundException("Employee not found with ID: ", id);
            }
        } catch (ResourceNotFoundException ex) {
            System.err.println("Error: " + ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            System.err.println("An unexpected error occurred: " + ex.getMessage()); // Log unexpected errors
        } finally {
            System.out.println("Delete operation attempted for employee with ID " + id);
        }
    }
}
