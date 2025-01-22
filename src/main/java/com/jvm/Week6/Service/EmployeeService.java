package com.jvm.Week6.Service;

import com.jvm.Week6.Entity.Employee;
import com.jvm.Week6.Entity.Project;
import com.jvm.Week6.Exception.ResourceNotFoundException;
import com.jvm.Week6.Repository.EmployeeRepo;
import com.jvm.Week6.Repository.ProjectRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
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

    @PersistenceContext
    private EntityManager entityManager;

    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }
    public List<Employee> getAllEmployees() {
        String hql = "FROM Employee";
        Query query = entityManager.createQuery(hql, Employee.class);
        return query.getResultList();
    }

    @Transactional
    public Employee getEmployeeById(Integer id) {
        String hql = "FROM Employee e WHERE e.id = :id";
        Query query = entityManager.createQuery(hql, Employee.class);
        query.setParameter("id", id);
        Employee employee = (Employee) query.getSingleResult();

        if (employee == null) {
            throw new ResourceNotFoundException("Employee not found with ID: " + id);
        }

        employee.getEmployeeProfile(); // Lazy load initialization
        employee.getEmployeeProfile().getAddress(); // Lazy load initialization
        employee.getAssignedProjects().size(); // Lazy load initialization

        return employee;
    }

    public List<Employee> getEmployeeByDesignation(String designation) {
        String hql = "FROM Employee e WHERE e.designation = :designation";
        Query query = entityManager.createQuery(hql, Employee.class);
        query.setParameter("designation", designation);
        return query.getResultList();
    }

    @Transactional
    public Employee assignProjectToEmployee(Integer empId, Integer projectId) {
        String employeeHql = "FROM Employee e WHERE e.id = :empId";
        Query employeeQuery = entityManager.createQuery(employeeHql, Employee.class);
        employeeQuery.setParameter("empId", empId);
        Employee employee = (Employee) employeeQuery.getSingleResult();

        if (employee == null) {
            throw new ResourceNotFoundException("Employee not found with ID: " + empId);
        }

        String projectHql = "FROM Project p WHERE p.id = :projectId";
        Query projectQuery = entityManager.createQuery(projectHql, Project.class);
        projectQuery.setParameter("projectId", projectId);
        Project project = (Project) projectQuery.getSingleResult();

        if (project == null) {
            throw new ResourceNotFoundException("Project not found with ID: " + projectId);
        }

        Set<Project> projects = employee.getAssignedProjects();
        projects.add(project);
        employee.setAssignedProjects(projects);

        entityManager.merge(employee);
        return employee;
    }

    @Transactional
    public Employee addEmployee(Employee e) {
        entityManager.persist(e);
        return e;
    }

    @Transactional
    public Employee updateEmployee(int id, Employee emp) {
        String hql = "FROM Employee e WHERE e.id = :id";
        Query query = entityManager.createQuery(hql, Employee.class);
        query.setParameter("id", id);
        Employee existingEmployee = (Employee) query.getSingleResult();

        if (existingEmployee == null) {
            throw new ResourceNotFoundException("Employee not found with ID: " + id);
        }

        existingEmployee.setDesignation(emp.getDesignation());
        existingEmployee.setSalary(emp.getSalary());

        entityManager.merge(existingEmployee);
        return existingEmployee;
    }

    @Transactional
    public void deleteEmployee(int id) {
        String hql = "FROM Employee e WHERE e.id = :id";
        Query query = entityManager.createQuery(hql, Employee.class);
        query.setParameter("id", id);
        Employee employee = (Employee) query.getSingleResult();

        if (employee == null) {
            throw new ResourceNotFoundException("Employee not found with ID: " + id);
        }

        entityManager.remove(employee);
    }

}
