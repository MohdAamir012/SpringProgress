package com.jvm.Week8.Service;

import com.jvm.Week8.Entity.Employee;
import com.jvm.Week8.Exception.ResourceNotFoundException;
import com.jvm.Week8.Repository.EmployeeRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Service
    public class EmployeeService {


    private final EmployeeRepo employeeRepo;
    private final PlatformTransactionManager transactionManager; // For programmatic transactions

    public EmployeeService(EmployeeRepo employeeRepo, PlatformTransactionManager transactionManager) {
        this.employeeRepo = employeeRepo;
        this.transactionManager = transactionManager;
    }
        // Use records for lightweight DTOs
        public record EmployeeRecord(String name, String designation,Integer yoe, Double salary) {}

        public List<Employee> getAllEmployees() {
            return employeeRepo.findAll();
        }

        public List<Employee> getEmployeeBySortedSalary() {
            List<Employee>employees= employeeRepo.findAll();

//            Using Navigable map to sort the employee based on salary in descending ordered.
            NavigableMap<Double, Employee> sortedEmployees = new TreeMap<>((s1, s2) -> Double.compare(s2, s1));
            for(Employee emp :employees){
                sortedEmployees.put(emp.getSalary(),emp);
            }
            sortedEmployees.descendingMap();

            return sortedEmployees.values().stream()
                    .collect(Collectors.toList());
        }

//        Using navigable set to find unique salaried employee
    public List<Employee> getEmployeeBySortedSalaryUsingNavigableSet() {
        List<Employee> employees = employeeRepo.findAll();

        // Using NavigableSet to store employees sorted by salary in descending order
        NavigableSet<Employee> sortedEmployees = new TreeSet<>((e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary()));
        sortedEmployees.addAll(employees);

        return new ArrayList<>(sortedEmployees);
    }
// Use of priority queue
    public List<Employee> getEmployeeBySortedSalaryUsingPriorityQueue() {
        List<Employee> employees = employeeRepo.findAll();

        // Using PriorityQueue (Max Heap) to sort employees based on salary in descending order
        PriorityQueue<Employee> queue = new PriorityQueue<>((e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary()));
        queue.addAll(employees);

        List<Employee> sortedList = new ArrayList<>();
        while (!queue.isEmpty()) {
            sortedList.add(queue.poll());
        }

        return sortedList;
    }

    // Using parallel stream to handle large data set
    public List<Employee> getEmployeeBySortedSalaryUsingParallelStream() {
        return employeeRepo.findAll().parallelStream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .collect(Collectors.toList());
    }
    // Using Concurrent Collections
    public List<Employee> getEmployeeBySortedSalaryUsingConcurrentCollection() {
        ConcurrentLinkedQueue<Employee> employeesQueue = new ConcurrentLinkedQueue<>(employeeRepo.findAll());

        return employeesQueue.parallelStream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .collect(Collectors.toList());
    }
    public EmployeeRecord getEmployeeById(int id) {
                Employee employee = employeeRepo.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("""
                            Employee not found with ID: %d, Please ensure the ID is correct.""".formatted(id), id));
                return new EmployeeRecord(employee.getName(), employee.getDesignation(),employee.getYoe(), employee.getSalary());
        }

    // Declarative Transaction Management (Auto Rollback on Failure)
    @Transactional
    public EmployeeRecord addEmployee(Employee e) {
        try {
            Employee savedEmployee = employeeRepo.save(e);
            return new EmployeeRecord(savedEmployee.getName(), savedEmployee.getDesignation(), savedEmployee.getYoe(), savedEmployee.getSalary());
        } catch (Exception ex) {
            throw new RuntimeException("Error while adding employee", ex);
        }
    }

    //  Programmatic Transaction Management (Manual Rollback Control)
    public EmployeeRecord addEmployeeWithProgrammaticTx(Employee e) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            Employee savedEmployee = employeeRepo.save(e);
            transactionManager.commit(status); // Commit transaction if everything is fine
            return new EmployeeRecord(savedEmployee.getName(), savedEmployee.getDesignation(), savedEmployee.getYoe(), savedEmployee.getSalary());
        } catch (Exception ex) {
            transactionManager.rollback(status); // Rollback transaction on error
            throw new RuntimeException("Error while adding employee", ex);
        }
    }

    @Transactional
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

@Transactional
        public void deleteEmployee(int id) {
                if (employeeRepo.existsById(id)) {
                    employeeRepo.deleteById(id);
                } else {
                    throw new ResourceNotFoundException("Employee not found with ID: ", id);
                }
        }
    }
