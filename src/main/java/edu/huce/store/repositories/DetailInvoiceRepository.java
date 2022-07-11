package edu.huce.store.repositories;

import java.util.List;

import edu.huce.store.exceptions.EtBadRequestException;
import edu.huce.store.exceptions.EtResourceNotFoundException;
import edu.huce.store.models.DetailInvoice;

public interface DetailInvoiceRepository {
    List<DetailInvoice> findAll() throws EtResourceNotFoundException;

    DetailInvoice findById(Integer id) throws EtResourceNotFoundException;

    Integer create(DetailInvoice detailInvoice) throws EtBadRequestException;
}
