package edu.huce.store.services;

import java.util.List;

import edu.huce.store.models.DetailInvoice;
import edu.huce.store.models.Invoice;

public interface InvoiceService {

    List<Invoice> fetchAllInvoices();

    List<Invoice> fetchInvoiceByTypeInvoice(String typeInvoice);

    Invoice fetchInvoiceById(Integer id);

    Invoice addInvoice(Invoice invoice, List<DetailInvoice> detailInvoices);
}
