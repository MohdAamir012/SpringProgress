package com.jvm.Week7.Service;

import com.jvm.Week7.Exception.ResourceNotFoundException;
import com.jvm.Week7.Entity.Employee;
import com.jvm.Week7.Repository.EmployeeRepo;
import org.springframework.stereotype.Service;

import java.util.List;

    @Service
    public class EmployeeService {

        private final EmployeeRepo employeeRepo;

        public EmployeeService(EmployeeRepo employeeRepo) {
            this.employeeRepo = employeeRepo;
        }

        // Use records for lightweight DTOs
        public record EmployeeRecord(Integer id, String designation, Double salary) {}

        public List<Employee> getAllEmployees() {
            return employeeRepo.findAll();
        }

        public EmployeeRecord getEmployeeById(int id) {
            try {
                Employee employee = employeeRepo.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("""
                            Employee not found with ID: %d, Please ensure the ID is correct.""".formatted(id), id));
                return new EmployeeRecord(employee.getId(), employee.getDesignation(), employee.getSalary());
            } catch (Exception e) {
                // Use pattern matching for exception handling
                if (e instanceof ResourceNotFoundException resourceNotFoundException) {
                    throw resourceNotFoundException;
                } else {
                    throw new RuntimeException("Unexpected error occurred", e);
                }
            }
        }

        public EmployeeRecord addEmployee(Employee e) {
            try {
                Employee savedEmployee = employeeRepo.save(e);
                return new EmployeeRecord(savedEmployee.getId(), savedEmployee.getDesignation(), savedEmployee.getSalary());
            } catch (Exception e1) {
                throw new RuntimeException("Error while adding employee", e1);
            }
        }

        public EmployeeRecord updateEmployee(int id, Employee emp) {
            try {
                Employee updatedEmployee = employeeRepo.findById(id)
                        .map(existingEmployee -> {
                            existingEmployee.setDesignation(emp.getDesignation());
                            existingEmployee.setSalary(emp.getSalary());
                            return employeeRepo.save(existingEmployee);
                        })
                        .orElseThrow(() -> new ResourceNotFoundException("""
                            Unable to update. Employee with ID: %d not found.""".formatted(id), id));

                return new EmployeeRecord(updatedEmployee.getId(), updatedEmployee.getDesignation(), updatedEmployee.getSalary());
            } catch (Exception e) {
                // Use pattern matching for exception handling
                if (e instanceof ResourceNotFoundException resourceNotFoundException) {
                    throw resourceNotFoundException;
                } else {
                    throw new RuntimeException("Unexpected error occurred", e);
                }
            }
        }


        public void deleteEmployee(int id) {
            try {
                String message = switch (id) { //Using Java 14: Switch expressions.
                    case 0 -> {
                        throw new ResourceNotFoundException("Employee ID cannot be zero", id);
                    }
                    default -> {
                        if (employeeRepo.existsById(id)) {
                            employeeRepo.deleteById(id);
                            yield "Employee deleted successfully";
                        } else {
                            throw new ResourceNotFoundException("Employee not found with ID: ", id);
                        }
                    }
                };
                System.out.println(message);  // You can log or use this message if necessary.
            } catch (Exception e) {
                // Use pattern matching for exception handling
                if (e instanceof ResourceNotFoundException resourceNotFoundException) {
                    throw resourceNotFoundException;
                } else {
                    throw new RuntimeException("Unexpected error occurred", e);
                }
            }
        }
    }
