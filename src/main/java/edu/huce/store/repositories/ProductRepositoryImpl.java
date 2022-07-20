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
import edu.huce.store.models.Product;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Product> findAll() {
        try {
            String SQL_FIND_ALL = "SELECT * FROM Products WHERE destroy = 0";
            List<Product> products = jdbcTemplate.query(SQL_FIND_ALL,
                    BeanPropertyRowMapper.newInstance(Product.class));
            return products;
        } catch (Exception e) {
            throw new EtBadRequestException(e.getMessage());
        }
    }

    @Override
    public Product findById(Integer id) {
        try {
            String SQL_FIND_BY_ID = "SELECT * FROM Products WHERE destroy = 0 AND id = " + id;
            List<Product> product = jdbcTemplate.query(SQL_FIND_BY_ID,
                    BeanPropertyRowMapper.newInstance(Product.class));
            if (product.size() == 0) {
                throw new EtResourceNotFoundException("Not found");

            } else {
                return product.get(0);
            }
        } catch (Exception e) {
            throw new EtBadRequestException(e.getMessage());
        }
    }

    @Override
    public Integer create(Product product) {
        try {
            String SQL_CREATE = "INSERT Products( name, priceBuy, priceSell, dateManufacture, dateExpiration, vendorId, quantity, image, note) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, product.getName());
                ps.setInt(2, product.getPriceBuy());
                ps.setInt(3, product.getPriceSell());
                ps.setString(4, product.getDateManufacture());
                ps.setString(5, product.getDateExpiration());
                ps.setInt(6, product.getVendorId());
                ps.setInt(7, product.getQuantity());
                ps.setString(8, product.getImage());
                ps.setString(9, product.getNote());
                return ps;
            }, keyHolder);
            return Integer.parseInt(keyHolder.getKeys().get("GENERATED_KEYS").toString());
        } catch (Exception e) {
            throw new EtBadRequestException("Invalid details. Failed to create account");
        }
    }

    @Override
    public Integer deleteById(Integer id) {
        try {
            String SQL_DELETE_BY_ID = "UPDATE Products SET destroy = 1 WHERE id = ?";
            jdbcTemplate.update(SQL_DELETE_BY_ID,
                    new Object[] { id });
            return id;
        } catch (Exception e) {
            throw new EtBadRequestException(e.getMessage());
        }
    }

    @Override
    public Integer update(Integer id, Product product) {
        try {
            String SQL_UPDATE = "UPDATE Products SET  name = ?, priceBuy = ?, priceSell = ?, dateManufacture = ?, dateExpiration = ?, vendorId = ?, quantity = ?, image = ?, note = ? WHERE id = ?";
            jdbcTemplate.update(
                    SQL_UPDATE,
                    new Object[] { product.getName(), product.getPriceBuy(), product.getPriceSell(),
                            product.getDateManufacture(), product.getDateExpiration(),
                            product.getVendorId(), product.getQuantity(), product.getImage(), product.getNote(),
                            id });
            return id;
        } catch (Exception e) {
            throw new EtBadRequestException("Update failed: " + e.getMessage());
        }
    }

}
