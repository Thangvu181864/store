package edu.huce.store.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.huce.store.exceptions.EtBadRequestException;
import edu.huce.store.exceptions.EtResourceNotFoundException;
import edu.huce.store.models.Employee;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(Employee employee) {
        try {
            String SQL_CREATE = "INSERT Employees(fullname, birthday, gender, address, phone, accountId) VALUES( ?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, employee.getFullname());
                ps.setString(2, employee.getBirthday());
                ps.setInt(3, employee.getGender());
                ps.setString(4, employee.getAddress());
                ps.setString(5, employee.getPhone());
                ps.setInt(6, employee.getAccountId());
                return ps;
            }, keyHolder);
            return Integer.parseInt(keyHolder.getKeys().get("GENERATED_KEYS").toString());
        } catch (Exception e) {
            throw new EtBadRequestException("Invalid details. Failed to create employee :" + e.getMessage());

        }
    }

    @Override
    public Employee findById(Integer id) {
        try {
            String SQL_FIND_BY_ID = "SELECT id, fullname, birthday, gender, address, phone, accountId FROM Employees WHERE destroy = 0 AND id = '"
                    + id + "'";
            List<Employee> employee = jdbcTemplate.query(SQL_FIND_BY_ID,
                    BeanPropertyRowMapper.newInstance(Employee.class));
            if (employee.size() == 0) {
                throw new EtResourceNotFoundException("Not found");
            } else {
                return employee.get(0);
            }
        } catch (Exception e) {
            throw new EtBadRequestException(e.getMessage());
        }

    }

    @Override
    public List<Employee> findAll() {
        try {
            String SQL_FIND_ALL = "SELECT id, fullname, birthday, gender, address, phone, accountId FROM Employees WHERE destroy = 0";
            List<Employee> employee = jdbcTemplate.query(SQL_FIND_ALL,
                    BeanPropertyRowMapper.newInstance(Employee.class));
            return employee;
        } catch (Exception e) {
            throw new EtBadRequestException(e.getMessage());
        }

    }

    @Override
    public Integer update(Integer id, Employee employee) {
        try {
            String SQL_UPDATE = "UPDATE Employees SET  fullname = ?, birthday = ?, gender = ?, address = ?, phone = ? WHERE destroy = 0 AND  id = ?";
            jdbcTemplate.update(
                    SQL_UPDATE,
                    new Object[] { employee.getFullname(), employee.getBirthday(), employee.getGender(),
                            employee.getAddress(), employee.getPhone(), id });
            return id;
        } catch (Exception e) {
            throw new EtBadRequestException("Update failed : " + e.getMessage());
        }
    }

    @Override
    public Integer deleteById(Integer id) {
        try {
            String SQL_DELETE_BY_ID = "UPDATE Employees SET destroy = 1 WHERE id = ?";
            jdbcTemplate.update(SQL_DELETE_BY_ID,
                    new Object[] { id });
            return id;
        } catch (Exception e) {
            throw new EtBadRequestException("Employee not found : " + e.getMessage());
        }
    }
}
