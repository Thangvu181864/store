package edu.huce.store.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.huce.store.exceptions.EtBadRequestException;
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
    public DetailInvoice fetchDetailInvoiceById(Integer id) {
        return detailInvoiceRepository.findById(id);
    }

    @Override
    public DetailInvoice addDetailInvoice(DetailInvoice detailInvoices) {
        if (detailInvoices.getProductId() != null && detailInvoices.getPriceSell() != null
                && detailInvoices.getQuantity() != null) {

            Integer id = detailInvoiceRepository.create(detailInvoices);
            return detailInvoiceRepository.findById(id);
        } else {
            throw new EtBadRequestException("Every field is requied");

        }
    }
}
