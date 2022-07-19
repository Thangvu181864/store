package edu.huce.store.services;

import java.util.List;

import edu.huce.store.exceptions.EtAuthException;
import edu.huce.store.exceptions.EtResourceNotFoundException;
import edu.huce.store.models.Employee;

public interface EmployeeService {
    Employee registerEmployee(Employee employee) throws EtAuthException;

    Employee fetchEmployeeById(Integer id) throws EtResourceNotFoundException;
    
    Employee updateEmployeeById(Integer id , Employee employee) throws EtResourceNotFoundException;
    
    Integer deleteEmployeeById(Integer id) throws EtResourceNotFoundException;

    List<Employee> fetchAllEmployees();
}
