package com.jvm.Week5.Service;

import com.jvm.Week5.Entity.Employee;
import com.jvm.Week5.Entity.Project;
import com.jvm.Week5.Exception.ResourceNotFoundException;
import com.jvm.Week5.Repository.EmployeeRepo;
import com.jvm.Week5.Repository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;

    @Autowired
    private ProjectRepo projectRepo;

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
    // Using custom exception class to throw proper message || Best practice fo rexception handling
    public List<Employee> getEmployeeByDesignation(String designation){
        List<Employee> employeeList = employeeRepo.findByDesignation(designation);
//        if (!employeeList.isEmpty()) {
        return employeeList;
    }

    public Employee assignProjectToEmployee(Integer empId,Integer ProjectId){
        Set<Project> projects =null;
        Employee employee =employeeRepo.findById(empId).get();
        Project project = projectRepo.findById(ProjectId).get();
        projects=employee.getAssignedProjects();
        projects.add(project);
        employee.setAssignedProjects(projects);
        employeeRepo.save(employee);
        return employee;
    }

    public Employee addEmployee(Employee e){
        return employeeRepo.save(e);
    }

    public Employee updateEmployee(int id, Employee emp) {
        Optional<Employee> existingEmployee = employeeRepo.findById(id);

        if (existingEmployee.isPresent()) {
            Employee employee = existingEmployee.get();
            employee.setDesignation(emp.getDesignation());
            employee.setSalary(emp.getSalary());
            employeeRepo.save(employee); // Update in DB
return employee;
        } else {
                throw new ResourceNotFoundException("Employee not found with ID: ", id);
            }
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
