package edu.huce.store.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.huce.store.exceptions.EtAuthException;
import edu.huce.store.exceptions.EtResourceNotFoundException;
import edu.huce.store.models.Employee;
import edu.huce.store.repositories.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Employee registerEmployee(Employee employee) throws EtAuthException {

        Integer id = employeeRepository.create(employee);
        return employeeRepository.findById(id);
    }

    @Override
    public Employee fetchEmployeeById(Integer id) throws EtResourceNotFoundException {
        return employeeRepository.findById(id);
    }

    @Override
    public List<Employee> fetchAllEmployees() {
        return employeeRepository.findAll();
    }

}
