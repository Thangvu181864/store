package edu.huce.store.repositories;

import java.util.List;

import edu.huce.store.models.Product;

public interface ProductRepository {
    List<Product> findAll();

    Product findById(Integer id);

    Integer deleteById(Integer id);

    Integer create(Product product);

    Integer update(Integer id, Product product);

}
