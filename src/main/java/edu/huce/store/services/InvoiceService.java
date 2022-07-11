package edu.huce.store.services;

import java.util.List;

import edu.huce.store.exceptions.EtResourceNotFoundException;
import edu.huce.store.models.DetailInvoice;
import edu.huce.store.models.Invoice;

public interface InvoiceService {
    
    List<Invoice> fetchAllInvoices();

    Invoice fetchInvoiceById(Integer id) throws EtResourceNotFoundException;

    Invoice addInvoice(Invoice invoice, List<DetailInvoice> detailInvoices);
}
