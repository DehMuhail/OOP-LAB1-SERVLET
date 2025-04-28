package com.misha.labam.servlet;

import com.misha.labam.entity.Product;
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

@WebServlet("/product")
public class ProductServlet extends HttpServlet {
    private ProductService productService;
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.productService = new ProductService();
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        req.setAttribute("product", productService.findById(Long.valueOf(pathInfo)));
        req.getRequestDispatcher("/WEB-INF/classes/views/product.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String stock = req.getParameter("stock");
        System.out.println(name + " " + price + " " + stock);
        productService.save(new Product(null,name,Double.parseDouble(price),Integer.parseInt(stock)));
        resp.sendRedirect("/products");
    }


    private boolean isAdmin(String userName) {
        User byEmail = userService.findByEmail(userName);
        if (byEmail.getRole().equals(Role.ADMIN)) {
            return true;
        } else return false;

    }
}
