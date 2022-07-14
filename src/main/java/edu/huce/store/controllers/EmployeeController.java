package edu.huce.store.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.huce.store.models.Employee;
import edu.huce.store.services.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Employee>> RegisterEmployee(
            @RequestBody Employee payload) {
        Employee employee = employeeService.registerEmployee(payload);
        Map<String, Employee> map = new HashMap<>();
        map.put("data", employee);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Map<String, Employee>> GetEmployeeById(@PathVariable("id") Integer id) {
        Employee employee = employeeService.fetchEmployeeById(id);
        Map<String, Employee> map = new HashMap<String, Employee>();
        map.put("data", employee);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Map<String, List<Employee>>> GetAllEmployees() {
        List<Employee> employees = employeeService.fetchAllEmployees();
        Map<String, List<Employee>> map = new HashMap<String, List<Employee>>();
        map.put("data", employees);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
