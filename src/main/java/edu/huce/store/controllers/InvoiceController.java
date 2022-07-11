package edu.huce.store.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.huce.store.models.DetailInvoice;
import edu.huce.store.models.Invoice;
import edu.huce.store.services.InvoiceService;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @GetMapping("")
    public ResponseEntity<Map<String, List<Invoice>>> GetAllInvoices() {
        List<Invoice> invoices = invoiceService.fetchAllInvoices();
        Map<String, List<Invoice>> map = new HashMap<String, List<Invoice>>();
        map.put("data", invoices);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Invoice>> GetInvoiceById(@PathVariable("id") Integer id) {
        Invoice invoice = invoiceService.fetchInvoiceById(id);
        Map<String, Invoice> map = new HashMap<String, Invoice>();
        map.put("data", invoice);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Invoice>> CreateInvoice(HttpServletRequest request,@RequestBody Invoice payload) {
        Invoice invoice = new Invoice();
        invoice.setEmployeeId((Integer) request.getAttribute("id"));
        invoice.setTypeInvoice(payload.getTypeInvoice());
        invoice.setNote(payload.getNote());
        List<DetailInvoice> detailInvoices = payload.getDetailInvoices();
        Invoice reslut = invoiceService.addInvoice(invoice, detailInvoices);
        Map<String, Invoice> map = new HashMap<>();
        map.put("message", reslut);
        return new ResponseEntity<>(map, HttpStatus.OK);

    }
}
