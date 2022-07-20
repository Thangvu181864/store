package edu.huce.store.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.huce.store.exceptions.EtAuthException;
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

    @GetMapping("/")
    public ResponseEntity<Map<String, List<Vendor>>> GetVendorByName(@RequestParam String name) {
        List<Vendor> vendors = vendorService.fectchVendorByName(name);
        Map<String, List<Vendor>> map = new HashMap<String, List<Vendor>>();
        map.put("data", vendors);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Vendor>> UpdateVendorById(HttpServletRequest request,
            @PathVariable("id") Integer id,
            @RequestBody Vendor payload) {
        if ((Integer) request.getAttribute("roleId") == 2) {
            Vendor vendor = vendorService.updateVendor(id, payload);
            Map<String, Vendor> map = new HashMap<String, Vendor>();
            map.put("data", vendor);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            throw new EtAuthException("Unauthorized");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> DeleteEmployeeById(HttpServletRequest request,
            @PathVariable("id") Integer id) {
        if ((Integer) request.getAttribute("roleId") == 2) {
            Integer vendorId = vendorService.deleteVendorById(id);
            Map<String, String> map = new HashMap<String, String>();
            map.put("data", "Delete employeeId: " + vendorId + " successful");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            throw new EtAuthException("Unauthorized");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Vendor>> CreateVendor(HttpServletRequest request,
            @RequestBody Vendor payload) {
        if ((Integer) request.getAttribute("roleId") == 2) {
            Vendor vendor = vendorService.addVendor(payload);
            Map<String, Vendor> map = new HashMap<>();
            map.put("data", vendor);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            throw new EtAuthException("Unauthorized");
        }
    }
}
