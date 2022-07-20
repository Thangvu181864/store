package edu.huce.store.services;

import java.util.List;

import edu.huce.store.models.DetailInvoice;

public interface DetailInvoiceService {
    List<DetailInvoice> fectchAllDetailInvoices();

    DetailInvoice fetchDetailInvoiceById(Integer id);

    DetailInvoice addDetailInvoice(DetailInvoice detailInvoices);
}
