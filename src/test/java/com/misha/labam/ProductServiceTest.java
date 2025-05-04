package com.misha.labam;

import com.misha.labam.entity.Product;
import com.misha.labam.service.ProductService;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    private final ProductService productService = new ProductService();


    @Test
    public void testFindAllProducts() {
        productService.addProduct(new Product(null, "One", 10.0, 100));
        productService.addProduct(new Product(null, "Two", 20.0, 200));
        List<Product> products = productService.getAllProducts();
        assertTrue(products.size() >= 2);
    }
}
