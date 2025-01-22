package com.jvm.Week6.Service;

import com.jvm.Week6.Entity.Employee;
import com.jvm.Week6.Entity.Project;
import com.jvm.Week6.Exception.ResourceNotFoundException;
import com.jvm.Week6.Repository.EmployeeRepo;
import com.jvm.Week6.Repository.ProjectRepo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
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

//    Using criteria API to coneect with database
    @Transactional
    public List<Employee> getAllEmployees() {
        try (Session session = sessionFactory.openSession()) {
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            JpaCriteriaQuery<Employee> query = builder.createQuery(Employee.class);
            JpaRoot<Employee> root = query.from(Employee.class);
            query.select(root);
            return session.createQuery(query).getResultList();
        }
    }
    @Transactional
    public Employee getEmployeeById(int id) {
        try (Session session = sessionFactory.openSession()) {
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            JpaCriteriaQuery<Employee> query = builder.createQuery(Employee.class);
            JpaRoot<Employee> root = query.from(Employee.class);
            query.select(root).where(builder.equal(root.get("id"), id));
            Employee employee = session.createQuery(query).uniqueResultOptional()
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));

            // Initialize assignedProjects
            employee.getAssignedProjects().size();  // Trigger lazy loading

            return employee;
        }
    }

    //    Here using JPQL query to do connect with database operations
    @Transactional
    public List<Employee> getEmployeeByDesignation(String designation) {
        try (Session session = sessionFactory.openSession()) {
            String jpql = "SELECT e FROM Employee e WHERE e.designation = :designation";
            return session.createQuery(jpql, Employee.class)
                    .setParameter("designation", designation)
                    .getResultList();
        }
    }


    //    Here using JPQL query to do connect with database operations
    @Transactional
    public Employee assignProjectToEmployee(Integer empId, Integer projectId) {
        try (Session session = sessionFactory.openSession()) {
            String employeeQuery = "SELECT e FROM Employee e WHERE e.id = :empId";
            Employee employee = session.createQuery(employeeQuery, Employee.class)
                    .setParameter("empId", empId)
                    .uniqueResultOptional()
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + empId));

            String projectQuery = "SELECT p FROM Project p WHERE p.id = :projectId";
            Project project = session.createQuery(projectQuery, Project.class)
                    .setParameter("projectId", projectId)
                    .uniqueResultOptional()
                    .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

            Set<Project> projects = employee.getAssignedProjects();
            projects.add(project);
            employee.setAssignedProjects(projects);

            session.getTransaction().begin();
            session.merge(employee);
            session.getTransaction().commit();
            return employee;
        }
    }

//    Using criteria API to coneect with database
    @Transactional
    public Employee addEmployee(Employee e) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.persist(e);
            session.getTransaction().commit();
            return e;
        }
    }

//    Using criteria API to coneect with database
    @Transactional
    public Employee updateEmployee(int id, Employee emp) {
        try (Session session = sessionFactory.openSession()) {
            Employee existingEmployee = session.find(Employee.class, id);
            if (existingEmployee == null) {
                throw new ResourceNotFoundException("Employee not found with ID: " + id);
            }

            existingEmployee.setDesignation(emp.getDesignation());
            existingEmployee.setSalary(emp.getSalary());
            session.getTransaction().begin();
            session.merge(existingEmployee);
            session.getTransaction().commit();
            return existingEmployee;
        }
    }

    //    Using criteria API to coneect with database
    @Transactional
    public void deleteEmployee(int id) {
        try (Session session = sessionFactory.openSession()) {
            Employee employee = session.find(Employee.class, id);
            if (employee == null) {
                throw new ResourceNotFoundException("Employee not found with ID: " + id);
            }

            session.getTransaction().begin();
            session.remove(employee);
            session.getTransaction().commit();
        }
    }
}
