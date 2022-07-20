package edu.huce.store.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.huce.store.exceptions.EtBadRequestException;
import edu.huce.store.models.Employee;
import edu.huce.store.repositories.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Employee registerEmployee(Employee employee) {
        if (employee.getFullname() != null && employee.getBirthday() != null && employee.getGender() != null
                && employee.getAddress() != null && employee.getPhone() != null && employee.getAccountId() != null) {
            Integer id = employeeRepository.create(employee);
            return employeeRepository.findById(id);
        } else {
            throw new EtBadRequestException("Every field is requied");
        }
    }

    @Override
    public Employee fetchEmployeeById(Integer id) {
        return employeeRepository.findById(id);
    }

    @Override
    public List<Employee> fetchAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployeeById(Integer id, Employee employee) {
        if (employee.getFullname() != null && employee.getBirthday() != null && employee.getGender() != null
                && employee.getAddress() != null && employee.getPhone() != null && employee.getAccountId() != null) {
            Integer employeeId = employeeRepository.update(id, employee);
            return employeeRepository.findById(employeeId);
        } else {
            throw new EtBadRequestException("Every field is requied");

        }
    }

    @Override
    public Integer deleteEmployeeById(Integer id) {
        Integer employeeId = employeeRepository.deleteById(id);
        return employeeId;
    }

}
