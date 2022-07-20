package edu.huce.store.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.huce.store.exceptions.EtAuthException;
import edu.huce.store.models.Employee;
import edu.huce.store.services.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Employee>> RegisterEmployee(
            HttpServletRequest request, @RequestBody Employee payload) {
        if ((Integer) request.getAttribute("roleId") == 2) {
            Employee employee = employeeService.registerEmployee(payload);
            Map<String, Employee> map = new HashMap<>();
            map.put("data", employee);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            throw new EtAuthException("Unauthorized");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Employee>> GetEmployeeById(
            @PathVariable("id") Integer id) {
        Employee employee = employeeService.fetchEmployeeById(id);
        Map<String, Employee> map = new HashMap<String, Employee>();
        map.put("data", employee);
        return new ResponseEntity<>(map, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Employee>> UpdateEmployeeById(HttpServletRequest request,
            @PathVariable("id") Integer id,
            @RequestBody Employee payload) {
        if ((Integer) request.getAttribute("roleId") == 2) {
            Employee employee = employeeService.updateEmployeeById(id, payload);
            Map<String, Employee> map = new HashMap<String, Employee>();
            map.put("data", employee);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            throw new EtAuthException("Unauthorized");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> DeleteEmployeeById(HttpServletRequest request,
            @PathVariable("id") Integer id) {
        if ((Integer) request.getAttribute("roleId") == 2) {
            Integer employeeId = employeeService.deleteEmployeeById(id);
            Map<String, String> map = new HashMap<String, String>();
            map.put("data", "Delete employeeId: " + employeeId + " successful");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            throw new EtAuthException("Unauthorized");
        }
    }

    @GetMapping("")
    public ResponseEntity<Map<String, List<Employee>>> GetAllEmployees() {
        List<Employee> employees = employeeService.fetchAllEmployees();
        Map<String, List<Employee>> map = new HashMap<String, List<Employee>>();
        map.put("data", employees);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
