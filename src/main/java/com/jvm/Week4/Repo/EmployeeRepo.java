package com.jvm.Week4.Repo;

import com.jvm.Week4.Entity.Employee;
import com.jvm.Week4.Exception.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepo {

    private final List<Employee> employeeList = new ArrayList<>();

    public EmployeeRepo(){
        employeeList.add(new Employee(1, "Shivam", "Senior Software Engineer", 60000));
        employeeList.add(new Employee(2, "Rohit", "Associate Tech lead", 85000));
    }

    public List<Employee> findAll(){
        return this.employeeList;
    }

    public Optional<Employee> findById(int id){
        return employeeList.stream().filter(e->e.getId()==id).findFirst();
    }
    public Employee save(Employee employee) {
        employeeList.add(employee);
        return employee;
    }

    public Employee update(int id,Employee e) {
        Optional<Employee> employeeOptional = findById(id);

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            employee.setName(e.getName());
            employee.setDesignation(e.getDesignation());
            employee.setSalary(e.getSalary());
            return employee;
        } else {
            throw new ResourceNotFoundException("No employee find with the id "+id);          // Return empty if the employee was not found
        }
    }

    public void deleteById(int id) {
        employeeList.removeIf(emp -> emp.getId() == id);
    }

    public boolean existsById(int id) {
        return employeeList.stream().anyMatch(emp -> emp.getId() == id);
    }
}
