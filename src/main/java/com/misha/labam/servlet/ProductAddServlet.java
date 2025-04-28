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

@WebServlet("/product/add")
public class ProductAddServlet extends HttpServlet {
    private ProductService productService;
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.productService = new ProductService();
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/classes/views/productadd.jsp").forward(req, resp);
    }

    private boolean isAdmin(String userName) {
        User byEmail = userService.findByEmail(userName);
        if (byEmail.getRole().equals(Role.ADMIN)) {
            return true;
        }
        else return false;
    }
}