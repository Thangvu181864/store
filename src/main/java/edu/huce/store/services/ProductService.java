package edu.huce.store.services;

import java.util.List;

import edu.huce.store.exceptions.EtAuthException;
import edu.huce.store.exceptions.EtResourceNotFoundException;
import edu.huce.store.models.Product;

public interface ProductService {
    List<Product> fectchAllProducts();

    Product fetchProductById(Integer id) throws EtResourceNotFoundException;

    Product deleteProductById(Integer id) throws EtResourceNotFoundException;

    Product addProduct(Product product)
            throws EtAuthException;

    Product updateProduct(Product product)
            throws EtResourceNotFoundException;
}
