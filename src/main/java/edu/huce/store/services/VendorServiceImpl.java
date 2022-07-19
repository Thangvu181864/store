package edu.huce.store.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.huce.store.exceptions.EtAuthException;
import edu.huce.store.exceptions.EtResourceNotFoundException;
import edu.huce.store.models.Vendor;
import edu.huce.store.repositories.VendorRepository;

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    VendorRepository vendorRepository;

    @Override
    public List<Vendor> fectchAllVendors() throws EtResourceNotFoundException {
        return vendorRepository.findAll();
    }

    @Override
    public Vendor addVendor(Vendor vendor) throws EtAuthException {
        Integer id = vendorRepository.create(vendor);
        return vendorRepository.findById(id);
    }

    @Override
    public Vendor updateVendor(Integer id, Vendor vendor) throws EtResourceNotFoundException {
        Integer vendorId = vendorRepository.update( id,  vendor);
        return vendorRepository.findById(vendorId);
    }

    @Override
    public Integer deleteVendorById(Integer id) throws EtResourceNotFoundException {
        Integer vendorId = vendorRepository.deleteById(id);
        return vendorId;
    }

    @Override
    public List<Vendor> fectchVendorByName(String param) throws EtResourceNotFoundException {
        return vendorRepository.findByName(param);
    }

}
