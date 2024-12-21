package com.jvm.Week3.Service;

import com.jvm.Week3.Entity.Employee;
import com.jvm.Week3.Repo.EmployeeRepo;
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
    public Optional<Employee> getEmployeeById(int id){
        return employeeRepo.findById(id);
    }
    public Employee addEmployee(Employee e){
        return employeeRepo.save(e);
    }
    public Employee updateEmployee(int id, Employee updatedEmployee) {
        Optional<Employee> existingEmployeeOpt = employeeRepo.findById(id);
            Employee existingEmployee = existingEmployeeOpt.get();
            existingEmployee.setName(updatedEmployee.getName());
            existingEmployee.setDesignation(updatedEmployee.getDesignation());
            existingEmployee.setSalary(updatedEmployee.getSalary());
            // Save the updated employee
            return employeeRepo.save(existingEmployee);
    }
    public void deleteEmployee(int id){
        employeeRepo.deleteById(id);
    }
}
