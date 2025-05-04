package com.misha.labam.servlet;

import com.misha.labam.entity.Role;
import com.misha.labam.entity.User;
import com.misha.labam.service.ProductService;
import com.misha.labam.service.UserService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/products")
public class ProductsServlet extends HttpServlet {

    private ProductService productService;
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.productService = new ProductService();
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            String name = req.getUserPrincipal().getName();
            User byEmail = userService.findByEmail(name);
            if (!byEmail.getRole().equals(Role.ADMIN)) {
                productService.getAllProducts();
                req.setAttribute("products", productService.getAllProducts());
                req.setAttribute("admin",false);
                req.getRequestDispatcher("/WEB-INF/classes/views/products.jsp").forward(req, resp);
            }else {
                productService.getAllProducts();
                req.setAttribute("products", productService.getAllProducts());
                req.setAttribute("admin",true);
                req.getRequestDispatcher("/WEB-INF/classes/views/products.jsp").forward(req, resp);
            }

        }catch (RuntimeException e){
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied "+e.getMessage());
        }
    }
}
