package edu.huce.store.repositories;

import java.util.List;

import edu.huce.store.exceptions.EtAuthException;
import edu.huce.store.exceptions.EtResourceNotFoundException;
import edu.huce.store.models.Vendor;

public interface VendorRepository {
    List<Vendor> findAll() throws EtResourceNotFoundException;

    Integer create(Vendor vendor) throws EtAuthException;

    Vendor findById(Integer id) throws EtResourceNotFoundException;

    List<Vendor> findByName(String param) throws EtResourceNotFoundException;

    Integer deleteById(Integer id) throws EtResourceNotFoundException;

    Integer update(Integer id, Vendor vendor) throws EtResourceNotFoundException;
}
