package edu.huce.store.repositories;

import java.util.List;

import edu.huce.store.exceptions.EtAuthException;
import edu.huce.store.exceptions.EtResourceNotFoundException;
import edu.huce.store.models.Product;

public interface ProductRepository {
    List<Product> findAll() throws EtResourceNotFoundException;

    Product findById(Integer id) throws EtResourceNotFoundException;

    Product deleteById(Integer id) throws EtResourceNotFoundException;

    Integer create(Product product) throws EtAuthException;

}
