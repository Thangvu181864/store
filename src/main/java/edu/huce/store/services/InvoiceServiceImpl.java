package edu.huce.store.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.huce.store.exceptions.EtBadRequestException;
import edu.huce.store.models.DetailInvoice;
import edu.huce.store.models.Invoice;
import edu.huce.store.repositories.DetailInvoiceRepository;
import edu.huce.store.repositories.InvoiceRepository;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    DetailInvoiceRepository detailInvoiceRepository;

    @Override
    public List<Invoice> fetchAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice fetchInvoiceById(Integer id) {
        return invoiceRepository.findById(id);
    }

    @Override
    public Invoice addInvoice(Invoice invoice, List<DetailInvoice> detailInvoices) {
        if (invoice.getTypeInvoice() != null && detailInvoices.size() > 0) {
            Integer invoicesId = invoiceRepository.create(invoice);
            List<DetailInvoice> result = new ArrayList<>();
            detailInvoices.forEach(detail -> {
                detail.setInvoiceId(invoicesId);
                Integer id = detailInvoiceRepository.create(detail);
                result.add(detailInvoiceRepository.findById(id));
            });
            return invoiceRepository.findById(invoicesId);
        } else {
            throw new EtBadRequestException("Every field is requied");
        }

    }

    @Override
    public List<Invoice> fetchInvoiceByTypeInvoice(String typeInvoice) {
        return invoiceRepository.findByTypeInvoice(typeInvoice);
    }

}
