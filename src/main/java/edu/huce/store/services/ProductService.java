package edu.huce.store.services;

import java.util.List;


import edu.huce.store.models.Product;

public interface ProductService {
        List<Product> fectchAllProducts();

        Product fetchProductById(Integer id);

        Integer deleteProductById(Integer id);

        Product addProduct(Product product);

        Product updateProduct(Integer id, Product product);
}
