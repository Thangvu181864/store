package edu.huce.store.repositories;

import java.util.List;

import edu.huce.store.models.DetailInvoice;

public interface DetailInvoiceRepository {
    List<DetailInvoice> findAll();

    DetailInvoice findById(Integer id);

    Integer create(DetailInvoice detailInvoice);
}
