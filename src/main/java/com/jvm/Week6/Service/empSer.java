package com.jvm.Week6.Service;

import com.jvm.Week6.Entity.Employee;
import com.jvm.Week6.Entity.Project;
import com.jvm.Week6.Exception.ResourceNotFoundException;
import com.jvm.Week6.Repository.EmployeeRepo;
import com.jvm.Week6.Repository.ProjectRepo;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class empSer {

    private final EmployeeRepo employeeRepo;

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private SessionFactory sessionFactory;



    public empSer(EmployeeRepo employeeRepo){
        this.employeeRepo=employeeRepo;
    }

    public List<Employee> getAllEmployees(){
        return employeeRepo.findAll();
    }
    //Using custom Session management
    @Transactional
    public Employee getEmployeeById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Employee employee = session.get(Employee.class, id);
            if (employee == null) {
                throw new ResourceNotFoundException("Employee not found with ID: " + id);
            }
            return employee;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching Employee with ID: " + id, e);
        }
    }

    public List<Employee> getEmployeeByDesignation(String designation){
        List<Employee> employeeList = employeeRepo.findByDesignation(designation);
        return employeeList;
    }

    public Employee assignProjectToEmployee(Integer empId, Integer ProjectId){
        Set<Project> projects =null;
        Employee employee =employeeRepo.findById(empId).get();
        Project project = projectRepo.findById(ProjectId).get();
        projects=employee.getAssignedProjects();
        projects.add(project);
        employee.setAssignedProjects(projects);
        employeeRepo.save(employee);
        return employee;
    }

    @Transactional
    public Employee addEmployee(Employee e){
        Session session = sessionFactory.getCurrentSession();
        session.persist(e);
        return e;
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

    /*
    @Transactional
public void updateEmployeeByIdWithHQL(int id, String newDesignation, Double newSalary) {
    try (Session session = sessionFactory.openSession()) {
        session.beginTransaction();

        // HQL update query
        String hql = "UPDATE Employee e SET e.designation = :designation, e.salary = :salary WHERE e.id = :id";
        int updatedRows = session.createQuery(hql)
                                 .setParameter("designation", newDesignation)
                                 .setParameter("salary", newSalary)
                                 .setParameter("id", id)
                                 .executeUpdate();

        if (updatedRows == 0) {
            throw new ResourceNotFoundException("Employee not found with ID: " + id);
        }

        session.getTransaction().commit();
    } catch (Exception e) {
        throw new RuntimeException("Error updating Employee with ID: " + id, e);
    }
}

     */
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
