package edu.huce.store.repositories;

import java.util.List;

import edu.huce.store.models.Invoice;

public interface InvoiceRepository {
    List<Invoice> findAll();

    List<Invoice> findByTypeInvoice(String typeInvoice);

    Invoice findById(Integer id);

    Integer create(Invoice invoice);

}
