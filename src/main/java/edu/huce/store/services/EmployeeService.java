package edu.huce.store.services;

import java.util.List;

import edu.huce.store.models.Employee;

public interface EmployeeService {
    Employee registerEmployee(Employee employee);

    Employee fetchEmployeeById(Integer id);

    Employee updateEmployeeById(Integer id, Employee employee);

    Integer deleteEmployeeById(Integer id);

    List<Employee> fetchAllEmployees();
}
