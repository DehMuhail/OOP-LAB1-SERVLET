package com.misha.labam.service;

import com.misha.labam.dao.ProductDao;
import com.misha.labam.entity.Product;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ProductService {
    private static final Logger logger = Logger.getLogger(ProductService.class.getName());
    private final ProductDao productDao = new ProductDao();

    public Product addProduct(Product product) {
        logger.info("Adding product: " + product.getName());
        productDao.save(product);
        logger.info("Product added with id: " + product.getId());
        return product;
    }

    public Optional<Product> getProductById(Long id) {
        logger.info("Retrieving product with id: " + id);
        return productDao.findById(id);
    }

    public List<Product> getAllProducts() {
        logger.info("Retrieving all products");
        return productDao.findAll();
    }

    public void updateProduct(Product product) {
        logger.info("Updating product with id: " + product.getId());
        productDao.update(product);
    }

    public void delete(Long id) {
        logger.info("Deleting product with id: " + id);
        productDao.delete(id);
    }
}
