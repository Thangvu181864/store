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
            String SQL_FIND_ALL = "SELECT * FROM Vendors WHERE destroy = 0";
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
        String SQL_FIND_BY_ID = "SELECT id, name, address, email, phone, note FROM Vendors WHERE destroy = 0 AND id = '"
                + id + "'";
        List<Vendor> employee = jdbcTemplate.query(SQL_FIND_BY_ID, BeanPropertyRowMapper.newInstance(Vendor.class));
        return employee.get(0);
    }

    @Override
    public Integer update(Integer id, Vendor vendor) throws EtResourceNotFoundException {
        try {
            String SQL_UPDATE = "UPDATE Vendors SET  name = ?, address = ?, email = ?, phone = ?, note = ? WHERE destroy = 0 AND  id = ?";
            jdbcTemplate.update(
                    SQL_UPDATE,
                    new Object[] { vendor.getName(), vendor.getAddress(), vendor.getEmail(), vendor.getPhone(),
                            vendor.getNote(), id });
            return id;
        } catch (Exception e) {
            throw new EtResourceNotFoundException("Update failed: " + e.getMessage());
        }
    }

    @Override
    public Integer deleteById(Integer id) throws EtResourceNotFoundException {
        try {
            String SQL_DELETE_BY_ID = "UPDATE Vendors SET destroy = 1 WHERE id = ?";
            jdbcTemplate.update(SQL_DELETE_BY_ID,
                    new Object[] { id });
            return id;
        } catch (Exception e) {
            throw new EtResourceNotFoundException("Vendor not found");
        }
    }

    @Override
    public List<Vendor> findByName(String param) throws EtResourceNotFoundException {
        String SQL_FIND_BY_NAME = "SELECT id, name, address, email, phone, note FROM Vendors WHERE destroy = 0 AND name LIKE '%"
                + param + "%'";
        List<Vendor> vendors = jdbcTemplate.query(SQL_FIND_BY_NAME, BeanPropertyRowMapper.newInstance(Vendor.class));
        return vendors;
    }

    

}
