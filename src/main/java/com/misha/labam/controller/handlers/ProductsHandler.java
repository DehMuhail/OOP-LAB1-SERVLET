package com.misha.labam.controller.handlers;

import com.misha.labam.controller.HttpHandlerBase;
import com.misha.labam.entity.Role;
import com.misha.labam.entity.User;
import com.misha.labam.service.ProductService;
import com.misha.labam.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public class ProductsHandler extends HttpHandlerBase {

    private final ProductService productService;
    private final UserService userService;

    public ProductsHandler(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
        addPath("^/products$");
    }

    @Override
    protected String doGet(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> model) throws ServletException, IOException {
        try {
            String name = req.getUserPrincipal().getName();
            User byEmail = userService.findByEmail(name);
            
            // Get all products regardless of user role
            model.put("products", productService.getAllProducts());
            
            // Set admin flag based on user role
            boolean isAdmin = byEmail.getRole().equals(Role.ADMIN);
            model.put("admin", isAdmin);
            
            return "products";
        } catch (RuntimeException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied " + e.getMessage());
            return null;
        }
    }
}