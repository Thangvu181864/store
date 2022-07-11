package edu.huce.store.services;

import java.util.List;

import edu.huce.store.exceptions.EtAuthException;
import edu.huce.store.models.Vendor;

public interface VendorService {
    List<Vendor> fectchAllVendors();

    Vendor addVendor(Vendor vendor) throws EtAuthException;
}
