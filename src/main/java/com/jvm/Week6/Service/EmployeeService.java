package com.jvm.Week6.Service;

import com.jvm.Week6.Entity.Employee;
import com.jvm.Week6.Entity.Project;
import com.jvm.Week6.Exception.ResourceNotFoundException;
import com.jvm.Week6.Repository.EmployeeRepo;
import com.jvm.Week6.Repository.ProjectRepo;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private SessionFactory sessionFactory;

    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    @Transactional
    public Employee getEmployeeById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            Employee employee = session.get(Employee.class, id);
            if (employee == null) {
                throw new ResourceNotFoundException("Employee not found with ID: " + id);
            }
            Hibernate.initialize(employee.getEmployeeProfile());
            Hibernate.initialize(employee.getEmployeeProfile());
            Hibernate.initialize(employee.getEmployeeProfile().getAddress());
            Hibernate.initialize(employee.getAssignedProjects());
            return employee;

        } catch (Exception e) {
            throw new RuntimeException("Error fetching Employee with ID: " + id, e);
        }

    }

    public List<Employee> getEmployeeByDesignation(String designation) {
        return employeeRepo.findByDesignation(designation);
    }

    @Transactional
    public Employee assignProjectToEmployee(Integer empId, Integer projectId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Employee employee = session.get(Employee.class, empId);
            if (employee == null) {
                throw new ResourceNotFoundException("Employee not found with ID: " + empId);
            }

            Project project = session.get(Project.class, projectId);
            if (project == null) {
                throw new ResourceNotFoundException("Project not found with ID: " + projectId);
            }

            Set<Project> projects = employee.getAssignedProjects();
            projects.add(project);
            employee.setAssignedProjects(projects);

            session.merge(employee);
            session.getTransaction().commit();
            return employee;
        } catch (Exception e) {
            throw new RuntimeException("Error assigning project to Employee with ID: " + empId, e);
        }
    }

    @Transactional
    public Employee addEmployee(Employee e) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(e);
            session.getTransaction().commit();
            return e;
        } catch (Exception ex) {
            throw new RuntimeException("Error adding Employee", ex);
        }
    }

    @Transactional
    public Employee updateEmployee(int id, Employee emp) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Employee existingEmployee = session.get(Employee.class, id);
            if (existingEmployee == null) {
                throw new ResourceNotFoundException("Employee not found with ID: " + id);
            }

            existingEmployee.setDesignation(emp.getDesignation());
            existingEmployee.setSalary(emp.getSalary());

            session.merge(existingEmployee);
            session.getTransaction().commit();
            return existingEmployee;
        } catch (Exception ex) {
            throw new RuntimeException("Error updating Employee with ID: " + id, ex);
        }
    }

    @Transactional
    public void deleteEmployee(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Employee employee = session.get(Employee.class, id);
            if (employee == null) {
                throw new ResourceNotFoundException("Employee not found with ID: " + id);
            }

            session.remove(employee);
            session.getTransaction().commit();
        } catch (ResourceNotFoundException ex) {
            System.err.println("Error: " + ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            System.err.println("An unexpected error occurred: " + ex.getMessage());
            throw new RuntimeException("Error deleting Employee with ID: " + id, ex);
        } finally {
            System.out.println("Delete operation attempted for employee with ID " + id);
        }
    }
}
