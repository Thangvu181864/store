package edu.huce.store.services;

import java.util.List;

import edu.huce.store.models.Vendor;

public interface VendorService {
    List<Vendor> fectchAllVendors();

    List<Vendor> fectchVendorByName(String param);

    Vendor updateVendor(Integer id, Vendor vendor);

    Integer deleteVendorById(Integer id);

    Vendor addVendor(Vendor vendor);
}
