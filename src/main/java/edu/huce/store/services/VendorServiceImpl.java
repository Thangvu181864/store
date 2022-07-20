package edu.huce.store.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.huce.store.exceptions.EtBadRequestException;
import edu.huce.store.models.Vendor;
import edu.huce.store.repositories.VendorRepository;

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    VendorRepository vendorRepository;

    @Override
    public List<Vendor> fectchAllVendors() {
        return vendorRepository.findAll();
    }

    @Override
    public Vendor addVendor(Vendor vendor) {
        if (vendor.getName() != null && vendor.getAddress() != null && vendor.getEmail() != null
                && vendor.getPhone() != null) {

            Integer id = vendorRepository.create(vendor);
            return vendorRepository.findById(id);
        } else {
            throw new EtBadRequestException("Every field is requied");
        }
    }

    @Override
    public Vendor updateVendor(Integer id, Vendor vendor) {
        if (vendor.getName() != null && vendor.getAddress() != null && vendor.getEmail() != null
                && vendor.getPhone() != null) {
            Integer vendorId = vendorRepository.update(id, vendor);
            return vendorRepository.findById(vendorId);
        } else {
            throw new EtBadRequestException("Every field is requied");
        }
    }

    @Override
    public Integer deleteVendorById(Integer id) {
        Integer vendorId = vendorRepository.deleteById(id);
        return vendorId;
    }

    @Override
    public List<Vendor> fectchVendorByName(String param) {
        return vendorRepository.findByName(param);
    }

}
