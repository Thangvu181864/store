package edu.huce.store.repositories;

import java.util.List;

import edu.huce.store.models.Employee;

public interface EmployeeRepository {
    Integer create(Employee employee);

    Employee findById(Integer id);

    List<Employee> findAll();

    Integer update(Integer id, Employee employee);

    Integer deleteById(Integer id);

}
