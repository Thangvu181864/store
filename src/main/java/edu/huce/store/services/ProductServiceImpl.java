package edu.huce.store.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.huce.store.exceptions.EtBadRequestException;
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
    public Product fetchProductById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public Product addProduct(Product product) {
        if (product.getName() != null && product.getPriceBuy() != null && product.getPriceSell() != null
                && product.getDateManufacture() != null && product.getDateExpiration() != null
                && product.getName() != null
                && product.getVendorId() != null && product.getQuantity() != null && product.getImage() != null) {
            Integer id = productRepository.create(product);
            return productRepository.findById(id);
        } else {
            throw new EtBadRequestException("Every field is requied");

        }
    }

    @Override
    public Integer deleteProductById(Integer id) {
        Integer productId = productRepository.deleteById(id);
        return productId;
    }

    @Override
    public Product updateProduct(Integer id, Product product) {
        if (product.getName() != null && product.getPriceBuy() != null && product.getPriceSell() != null
                && product.getDateManufacture() != null && product.getDateExpiration() != null
                && product.getName() != null
                && product.getVendorId() != null && product.getQuantity() != null && product.getImage() != null) {
            Integer productId = productRepository.update(id, product);
            return productRepository.findById(productId);
        } else {
            throw new EtBadRequestException("Every field is requied");

        }
    }

}
