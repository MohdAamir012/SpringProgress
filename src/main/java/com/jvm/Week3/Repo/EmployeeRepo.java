package com.jvm.Week3.Repo;

import com.jvm.Week3.Entity.Employee;
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

    public void deleteById(int id) {
        employeeList.removeIf(emp -> emp.getId() == id);
    }

}
