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

public class ProductDeleteHandler extends HttpHandlerBase {
    
    private final ProductService productService;
    private final UserService userService;

    public ProductDeleteHandler(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
        addPath("^/product/delete$");
    }

    @Override
    protected String doPost(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> model) throws ServletException, IOException {
        try {
            String userName = req.getUserPrincipal().getName();
            if (isAdmin(userName)) {
                String parameter = req.getParameter("id");
                productService.delete(Long.valueOf(parameter));
                return "/products@redirect";
            } else {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Only admins can delete products");
                return null;
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error deleting product: " + e.getMessage());
            return null;
        }
    }

    private boolean isAdmin(String userName) {
        User byEmail = userService.findByEmail(userName);
        return byEmail != null && byEmail.getRole().equals(Role.ADMIN);
    }
}