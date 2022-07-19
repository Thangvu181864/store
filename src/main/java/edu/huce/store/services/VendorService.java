package edu.huce.store.services;

import java.util.List;

import edu.huce.store.exceptions.EtAuthException;
import edu.huce.store.exceptions.EtResourceNotFoundException;
import edu.huce.store.models.Vendor;

public interface VendorService {
    List<Vendor> fectchAllVendors() throws EtResourceNotFoundException;

    List<Vendor> fectchVendorByName(String param) throws EtResourceNotFoundException;

    Vendor updateVendor(Integer id, Vendor vendor) throws EtResourceNotFoundException;

    Integer deleteVendorById(Integer id) throws EtResourceNotFoundException;

    Vendor addVendor(Vendor vendor) throws EtAuthException;
}
