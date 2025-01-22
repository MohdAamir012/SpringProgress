package com.jvm.Week6.Controller;

import com.jvm.Week6.Entity.Employee;
import com.jvm.Week6.Service.EmployeeService;
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

        Employee employee = employeeService.getEmployeeById(id);
        System.out.println("got the employee"+employee.getDesignation());
        return new ResponseEntity<Employee>(employee,HttpStatus.OK);
    }
    //Get employee by Name
    @GetMapping("designation/{designation}")
    public ResponseEntity<List<Employee>> getEmployeeByDesignation(@PathVariable("designation") String designation) {
        List<Employee> employeeList = employeeService.getEmployeeByDesignation(designation);
        return new ResponseEntity<List<Employee>>(employeeList,HttpStatus.OK);
    }

    // Add a new employee
    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.addEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }
    // Update employee by id
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        Employee savedEmployee = employeeService.updateEmployee(id,employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }
    @PutMapping("/{empId}/project/{projectId}")
    public Employee assignProjectToEmployee(
            @PathVariable Integer empId,
            @PathVariable Integer projectId
    ){
        return employeeService.assignProjectToEmployee(empId,projectId);
    }

    // Delete an employee by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
