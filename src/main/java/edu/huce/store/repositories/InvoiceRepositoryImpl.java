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
import edu.huce.store.models.Invoice;

@Repository
public class InvoiceRepositoryImpl implements InvoiceRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Invoice> findAll() {
        try {
            String SQL_FIND_ALL = "SELECT * FROM Invoices WHERE destroy = 0";
            List<Invoice> invoices = jdbcTemplate.query(SQL_FIND_ALL,
                    BeanPropertyRowMapper.newInstance(Invoice.class));

            for (int i = 0; i < invoices.size(); i++) {
                String SQL_FIND_DETAIL_BY_ID = "SELECT * FROM DetailInvoices WHERE destroy = 0 AND invoiceId = "
                        + invoices.get(i).getId();
                List<DetailInvoice> detailInvoices = jdbcTemplate.query(SQL_FIND_DETAIL_BY_ID,
                        BeanPropertyRowMapper.newInstance(DetailInvoice.class));
                invoices.get(i).setDetailInvoices(detailInvoices);
            }
            return invoices;
        } catch (Exception e) {
            throw new EtBadRequestException(e.getMessage());
        }
    }

    @Override
    public Invoice findById(Integer id) {
        try {
            String SQL_FIND_INVOICE_BY_ID = "SELECT * FROM Invoices WHERE destroy = 0 AND id = " + id;
            String SQL_FIND_DETAIL_BY_ID = "SELECT * FROM DetailInvoices WHERE destroy = 0 AND invoiceId = " + id;
            List<Invoice> invoices = jdbcTemplate.query(SQL_FIND_INVOICE_BY_ID,
                    BeanPropertyRowMapper.newInstance(Invoice.class));
           
            if (invoices.size() == 0) {
                throw new EtResourceNotFoundException("Not found");
            } else {
                List<DetailInvoice> detailInvoices = jdbcTemplate.query(SQL_FIND_DETAIL_BY_ID,
                BeanPropertyRowMapper.newInstance(DetailInvoice.class));
                invoices.get(0).setDetailInvoices(detailInvoices);
                return invoices.get(0);
            }
        } catch (Exception e) {
            throw new EtBadRequestException(e.getMessage());
        }
    }

    @Override
    public Integer create(Invoice invoice) {
        try {
            String SQL_CREATE = "INSERT Invoices(employeeId, typeInvoice, note) VALUES(?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, invoice.getEmployeeId());
                ps.setString(2, invoice.getTypeInvoice());
                ps.setString(3, invoice.getNote());
                return ps;
            }, keyHolder);
            return Integer.parseInt(keyHolder.getKeys().get("GENERATED_KEYS").toString());
        } catch (Exception e) {
            throw new EtBadRequestException("Invalid details. Failed to create invoice : " + e.getMessage());
        }
    }

    @Override
    public List<Invoice> findByTypeInvoice(String typeInvoice) {
        try {
            String SQL_FIND_ALL = "SELECT * FROM Invoices WHERE destroy = 0 AND typeInvoice = '" + typeInvoice + "'";
            List<Invoice> invoices = jdbcTemplate.query(SQL_FIND_ALL,
                    BeanPropertyRowMapper.newInstance(Invoice.class));

            for (int i = 0; i < invoices.size(); i++) {
                String SQL_FIND_DETAIL_BY_ID = "SELECT * FROM DetailInvoices WHERE destroy = 0 AND invoiceId = "
                        + invoices.get(i).getId();
                List<DetailInvoice> detailInvoices = jdbcTemplate.query(SQL_FIND_DETAIL_BY_ID,
                        BeanPropertyRowMapper.newInstance(DetailInvoice.class));
                invoices.get(i).setDetailInvoices(detailInvoices);
            }
            return invoices;
        } catch (Exception e) {
            throw new EtBadRequestException( e.getMessage());
        }
    }

}
