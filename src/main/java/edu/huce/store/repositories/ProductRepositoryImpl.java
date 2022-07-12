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
import edu.huce.store.models.Product;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Product> findAll() throws EtResourceNotFoundException {
        try {
            String SQL_FIND_ALL = "SELECT * FROM Products WHERE destroy = 0";
            List<Product> products = jdbcTemplate.query(SQL_FIND_ALL,
                    BeanPropertyRowMapper.newInstance(Product.class));
            return products;
        } catch (Exception e) {
            throw new EtResourceNotFoundException("Products not found");
        }
    }

    @Override
    public Product findById(Integer id) throws EtResourceNotFoundException {
        try {
            String SQL_FIND_BY_ID = "SELECT * FROM Products WHERE destroy = 0 AND id = " + id;
            List<Product> product = jdbcTemplate.query(SQL_FIND_BY_ID,
                    BeanPropertyRowMapper.newInstance(Product.class));
            return product.get(0);
        } catch (Exception e) {
            throw new EtResourceNotFoundException("Product not found");
        }
    }

    @Override
    public Integer create(Product product)
            throws EtAuthException {
        try {
            String SQL_CREATE = "INSERT Products( name, priceBuy, priceSell, vendorId, quantity, image, note) VALUES( ?, ?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, product.getName());
                ps.setInt(2, product.getPriceBuy());
                ps.setInt(3, product.getPriceSell());
                ps.setInt(4, product.getVendorId());
                ps.setInt(5, product.getQuantity());
                ps.setString(6, product.getImage());
                ps.setString(7, product.getNote());
                return ps;
            }, keyHolder);
            return Integer.parseInt(keyHolder.getKeys().get("GENERATED_KEYS").toString());
        } catch (Exception e) {
            throw new EtAuthException("Invalid details. Failed to create account");
        }
    }

    @Override
    public Product deleteById(Integer id) throws EtResourceNotFoundException {
        try {
            String SQL_DELETE_BY_ID = "UPDATE Products SET destroy = 1 Products WHERE id = " + id;
            List<Product> product = jdbcTemplate.query(SQL_DELETE_BY_ID,
                    BeanPropertyRowMapper.newInstance(Product.class));
            return product.get(0);
        } catch (Exception e) {
            throw new EtResourceNotFoundException("Product not found");
        }
    }

    @Override
    public Integer update(Product product) throws EtResourceNotFoundException {
        try {
            String SQL_UPDATE = "UPDATE Products SET  name = ?, priceBuy = ?, priceSell = ?, vendorId = ?, quantity = ?, image = ?, note = ? WHERE id = ?";
            jdbcTemplate.update(
                    SQL_UPDATE,
                    new Object[] { product.getName(), product.getPriceBuy(), product.getPriceSell(),
                            product.getVendorId(), product.getQuantity(), product.getImage(), product.getNote(),
                            product.getId() });
            return product.getId();
        } catch (Exception e) {
            throw new EtResourceNotFoundException("Update failed: " + e.getMessage());
        }
    }

}
