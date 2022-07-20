package edu.huce.store.repositories;

import java.util.List;

import edu.huce.store.models.Vendor;

public interface VendorRepository {
    List<Vendor> findAll();

    Integer create(Vendor vendor);

    Vendor findById(Integer id);

    List<Vendor> findByName(String param);

    Integer deleteById(Integer id);

    Integer update(Integer id, Vendor vendor);
}
