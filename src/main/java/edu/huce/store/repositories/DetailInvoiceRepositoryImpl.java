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
import edu.huce.store.models.DetailInvoice;

@Repository
public class DetailInvoiceRepositoryImpl implements DetailInvoiceRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<DetailInvoice> findAll() throws EtResourceNotFoundException {
        try {
            String SQL_FIND_ALL = "SELECT * FROM DetailInvoices";
            List<DetailInvoice> detailInvoices = jdbcTemplate.query(SQL_FIND_ALL,
                    BeanPropertyRowMapper.newInstance(DetailInvoice.class));
            return detailInvoices;
        } catch (Exception e) {
            throw new EtResourceNotFoundException("Invoices not found");
        }
    }

    @Override
    public DetailInvoice findById(Integer id) throws EtResourceNotFoundException {
        try {
            String SQL_FIND_BY_ID = "SELECT * FROM DetailInvoices WHERE id = " + id;
            List<DetailInvoice> detailInvoices = jdbcTemplate.query(SQL_FIND_BY_ID,
                    BeanPropertyRowMapper.newInstance(DetailInvoice.class));
            return detailInvoices.get(0);
        } catch (Exception e) {
            throw new EtResourceNotFoundException("Invoice not found");
        }
    }

    @Override
    public Integer create(DetailInvoice detailInvoice) throws EtBadRequestException {
        try {
            String SQL_CREATE = "INSERT DetailInvoices( invoiceId, productId, priceSell, quantity, total, note) VALUES(?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, detailInvoice.getInvoiceId());
                ps.setInt(2, detailInvoice.getProductId());
                ps.setInt(3, detailInvoice.getPriceSell());
                ps.setInt(4, detailInvoice.getQuantity());
                ps.setInt(5, detailInvoice.getPriceSell() * detailInvoice.getQuantity());
                ps.setString(6, detailInvoice.getNote());
                return ps;
            }, keyHolder);
            return Integer.parseInt(keyHolder.getKeys().get("GENERATED_KEYS").toString());
        } catch (Exception e) {
            throw new EtBadRequestException("Invalid details. Failed to create detail invoice");
        }
    }

}
