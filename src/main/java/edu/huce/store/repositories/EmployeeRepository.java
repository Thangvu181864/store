package edu.huce.store.repositories;

import java.util.List;

import edu.huce.store.exceptions.EtAuthException;
import edu.huce.store.exceptions.EtResourceNotFoundException;
import edu.huce.store.models.Employee;

public interface EmployeeRepository {
    Integer create(Employee employee) throws EtAuthException;

    Employee findById(Integer id);

    List<Employee> findAll() throws EtResourceNotFoundException;

}
