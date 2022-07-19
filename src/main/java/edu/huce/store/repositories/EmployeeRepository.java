package edu.huce.store.repositories;

import java.util.List;

import edu.huce.store.exceptions.EtAuthException;
import edu.huce.store.exceptions.EtResourceNotFoundException;
import edu.huce.store.models.Employee;

public interface EmployeeRepository {
    Integer create(Employee employee) throws EtAuthException;

    Employee findById(Integer id) throws EtResourceNotFoundException;
    
    List<Employee> findAll() throws EtResourceNotFoundException;
    
    Integer update(Integer id, Employee employee) throws EtResourceNotFoundException;
    
    Integer deleteById(Integer id) throws EtResourceNotFoundException;

}
