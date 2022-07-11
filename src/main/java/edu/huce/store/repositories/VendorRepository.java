package edu.huce.store.repositories;

import java.util.List;

import edu.huce.store.exceptions.EtAuthException;
import edu.huce.store.exceptions.EtResourceNotFoundException;
import edu.huce.store.models.Vendor;

public interface VendorRepository {
    List<Vendor> findAll() throws EtResourceNotFoundException;

    Integer create(Vendor vendor) throws EtAuthException;

    Vendor findById(Integer id);
}
