package com.jvm.Week4.Controller;

import com.jvm.Week4.Entity.Employee;
import com.jvm.Week4.Exception.ResourceNotFoundException;
import com.jvm.Week4.Service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {


    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService=employeeService;
    }

    //Get all employees
    @GetMapping()
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee>employeeList= employeeService.getAllEmployees();
        return new ResponseEntity<List<Employee>>(employeeList,HttpStatus.OK);
    }

    //Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
    // Using custom exception class to throw proper message || Best practice fo rexception handling
        if (id<=0) {
            throw new ResourceNotFoundException("Employee ID : " + id);
        }
        return new ResponseEntity<Employee>(employeeService.getEmployeeById(id),HttpStatus.OK);
    }

    // Add a new employee
    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.addEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }
    // Update employee by id
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        Employee savedEmployee = employeeService.updateEmployee(id,employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    // Delete an employee by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
