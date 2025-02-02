package com.jvm.Week8.Controller;

import com.jvm.Week8.Entity.Employee;
import com.jvm.Week8.Exception.EmployeeResponse;
import com.jvm.Week8.Service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping()
    public ResponseEntity<List<EmployeeService.EmployeeRecord>> getAllEmployees() {
            List<EmployeeService.EmployeeRecord> employeeList = employeeService.getAllEmployees()
                    .stream()
                    .map(emp -> new EmployeeService.EmployeeRecord(emp.getName(),emp.getDesignation(), emp.getYoe(),emp.getSalary()))
                    .toList();
            return ResponseEntity.ok(employeeList);
    }
    @GetMapping("/salary")
    public ResponseEntity<List<EmployeeService.EmployeeRecord>> getEmployeeByOrderedBySalary() {
        List<EmployeeService.EmployeeRecord> employeeList = employeeService.getEmployeeBySortedSalary()
                .stream()
                .map(emp -> new EmployeeService.EmployeeRecord(emp.getName(),emp.getDesignation(), emp.getYoe(),emp.getSalary()))
                .toList();
        return ResponseEntity.ok(employeeList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable int id) {
        try {
            EmployeeService.EmployeeRecord employee = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(new EmployeeResponse.Success<>( employee));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new EmployeeResponse.Error("Employee not found", 404));
        }
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> addEmployee(@RequestBody EmployeeService.EmployeeRecord record) {
            var employee = new Employee();
            employee.setName(record.name());
            employee.setDesignation(record.designation());
            employee.setYoe(record.yoe());
            employee.setSalary(record.salary());
            EmployeeService.EmployeeRecord savedEmployee = employeeService.addEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new EmployeeResponse.Success<>( savedEmployee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Integer id, @RequestBody EmployeeService.EmployeeRecord record) {
        try {
            var employee = new Employee();
            employee.setDesignation(record.designation());
            employee.setSalary(record.salary());
            EmployeeService.EmployeeRecord updatedEmployee = employeeService.updateEmployee(id, employee);
            return ResponseEntity.ok(new EmployeeResponse.Success<>(updatedEmployee));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new EmployeeResponse.Error("Failed to update employee", 404));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeResponse> deleteEmployee(@PathVariable int id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok(new EmployeeResponse.Success<>(null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new EmployeeResponse.Error("Failed to delete employee", 404));
        }
    }
}
