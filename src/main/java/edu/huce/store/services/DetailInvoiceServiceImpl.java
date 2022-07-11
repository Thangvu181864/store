package edu.huce.store.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.huce.store.exceptions.EtResourceNotFoundException;
import edu.huce.store.models.DetailInvoice;
import edu.huce.store.repositories.DetailInvoiceRepository;

@Service
public class DetailInvoiceServiceImpl implements DetailInvoiceService {

    @Autowired
    DetailInvoiceRepository detailInvoiceRepository;

    @Override
    public List<DetailInvoice> fectchAllDetailInvoices() {
        return detailInvoiceRepository.findAll();
    }

    @Override
    public DetailInvoice fetchDetailInvoiceById(Integer id) throws EtResourceNotFoundException {
        return detailInvoiceRepository.findById(id);
    }

    @Override
    public DetailInvoice addDetailInvoice(DetailInvoice detailInvoices) {
        Integer id = detailInvoiceRepository.create(detailInvoices);
        return detailInvoiceRepository.findById(id);
    }
}
