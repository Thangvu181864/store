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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.huce.store.models.Product;
import edu.huce.store.services.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("")
    public ResponseEntity<Map<String, List<Product>>> GetAllProducts() {
        List<Product> products = productService.fectchAllProducts();
        Map<String, List<Product>> map = new HashMap<String, List<Product>>();
        map.put("data", products);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Product>> GetProductById(@PathVariable("id") Integer id) {
        Product product = productService.fetchProductById(id);
        Map<String, Product> map = new HashMap<String, Product>();
        map.put("data", product);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Product>> CreateProduct(HttpServletRequest request,
            @RequestBody Product payload) {
        Product product = productService.addProduct(payload);
        Map<String, Product> map = new HashMap<>();
        map.put("data", product);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> DeleteProductById(@PathVariable("id") Integer id) {
        Integer productId = productService.deleteProductById(id);
        Map<String, String> map = new HashMap<String, String>();
        map.put("data", "Delete productId: " + productId + " successful");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Product>> UpdateProduct(HttpServletRequest request,
            @RequestBody Product payload) {
        Product product = productService.updateProduct(payload);
        Map<String, Product> map = new HashMap<>();
        map.put("data", product);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
