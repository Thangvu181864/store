package edu.huce.store.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.huce.store.exceptions.EtAuthException;
import edu.huce.store.exceptions.EtResourceNotFoundException;
import edu.huce.store.models.Product;
import edu.huce.store.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> fectchAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product fetchProductById(Integer id) throws EtResourceNotFoundException {
        return productRepository.findById(id);
    }

    @Override
    public Product addProduct(Product product)
            throws EtAuthException {
        Integer id = productRepository.create(product);
        return productRepository.findById(id);
    }

    @Override
    public Product deleteProductById(Integer id) throws EtResourceNotFoundException {
        return productRepository.deleteById(id);
    }

}
