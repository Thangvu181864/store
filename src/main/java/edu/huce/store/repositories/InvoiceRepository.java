package edu.huce.store.repositories;

import java.util.List;

import edu.huce.store.exceptions.EtBadRequestException;
import edu.huce.store.exceptions.EtResourceNotFoundException;
import edu.huce.store.models.Invoice;

public interface InvoiceRepository {
    List<Invoice> findAll() throws EtResourceNotFoundException;

    Invoice findById(Integer id) throws EtResourceNotFoundException;

    Integer create(Invoice invoice) throws EtBadRequestException;

}
