package com.jvm.Week9.Service;

import com.jvm.Week5.Exception.ResourceNotFoundException;
import com.jvm.Week9.Entity.Employee;
import com.jvm.Week9.Repository.EmployeeRepo;
import org.springframework.stereotype.Service;

import java.util.List;

    @Service
    public class EmployeeService {

        private final EmployeeRepo employeeRepo;

        public EmployeeService(EmployeeRepo employeeRepo) {
            this.employeeRepo = employeeRepo;
        }

        // Use records for lightweight DTOs
        public record EmployeeRecord(String name, String designation,Integer yoe, Double salary) {}

        public List<Employee> getAllEmployees() {
            return employeeRepo.findAll();
        }

        public EmployeeRecord getEmployeeById(int id) {
                Employee employee = employeeRepo.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("""
                            Employee not found with ID: %d, Please ensure the ID is correct.""".formatted(id), id));
                return new EmployeeRecord(employee.getName(), employee.getDesignation(),employee.getYoe(), employee.getSalary());
        }

        public EmployeeRecord addEmployee(Employee e) {
            try {
                Employee savedEmployee = employeeRepo.save(e);
                return new EmployeeRecord(savedEmployee.getName(),savedEmployee.getDesignation(),savedEmployee.getYoe(), savedEmployee.getSalary());
            } catch (Exception e1) {
                throw new RuntimeException("Error while adding employee", e1);
            }
        }

        public EmployeeRecord updateEmployee(int id, Employee emp) {
                Employee updatedEmployee = employeeRepo.findById(id)
                        .map(existingEmployee -> {
                            existingEmployee.setDesignation(emp.getDesignation());
                            existingEmployee.setSalary(emp.getSalary());
                            return employeeRepo.save(existingEmployee);
                        })
                        .orElseThrow(() -> new ResourceNotFoundException("""
                            Unable to update. Employee with ID: %d not found.""".formatted(id), id));
                return new EmployeeRecord(updatedEmployee.getName(), updatedEmployee.getDesignation(), updatedEmployee.getYoe(), updatedEmployee.getSalary());
        }


        public void deleteEmployee(int id) {
                if (employeeRepo.existsById(id)) {
                    employeeRepo.deleteById(id);
                } else {
                    throw new ResourceNotFoundException("Employee not found with ID: ", id);
                }
        }
    }
