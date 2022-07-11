package edu.huce.store.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.huce.store.models.Vendor;
import edu.huce.store.services.VendorService;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {
    @Autowired
    VendorService vendorService;

    @GetMapping("")
    public ResponseEntity<Map<String, List<Vendor>>> GetAllVendor() {
        List<Vendor> vendors = vendorService.fectchAllVendors();
        Map<String, List<Vendor>> map = new HashMap<String, List<Vendor>>();
        map.put("data", vendors);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Vendor>> CreateVendor(HttpServletRequest request,
            @RequestBody Vendor payload) {
        Vendor vendor = vendorService.addVendor(payload);
        Map<String, Vendor> map = new HashMap<>();
        map.put("data", vendor);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
