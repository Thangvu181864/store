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

import edu.huce.store.exceptions.EtAuthException;
import edu.huce.store.exceptions.EtResourceNotFoundException;
import edu.huce.store.models.Vendor;

@Repository
public class VendorRepositoryImpl implements VendorRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Vendor> findAll() throws EtResourceNotFoundException {
        try {
            String SQL_FIND_ALL = "SELECT * FROM Vendors";
            List<Vendor> vendors = jdbcTemplate.query(SQL_FIND_ALL,
                    BeanPropertyRowMapper.newInstance(Vendor.class));
            return vendors;
        } catch (Exception e) {
            throw new EtResourceNotFoundException("Products not found");
        }
    }

    @Override
    public Integer create(Vendor vendor) throws EtAuthException {
        try {
            String SQL_CREATE = "INSERT Vendors(name, address, email, phone, note) VALUES( ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, vendor.getName());
                ps.setString(2, vendor.getAddress());
                ps.setString(3, vendor.getEmail());
                ps.setString(4, vendor.getPhone());
                ps.setString(5, vendor.getNote());
                return ps;
            }, keyHolder);
            return Integer.parseInt(keyHolder.getKeys().get("GENERATED_KEYS").toString());
        } catch (Exception e) {
            throw new EtAuthException("Invalid details. Failed to create vendors");

        }
    }

    @Override
    public Vendor findById(Integer id) {
        String SQL_FIND_BY_ID = "SELECT id, name, address, email, phone, note FROM Vendors WHERE id = '"
                + id + "'";
        List<Vendor> employee = jdbcTemplate.query(SQL_FIND_BY_ID, BeanPropertyRowMapper.newInstance(Vendor.class));
        return employee.get(0);
    }

}
