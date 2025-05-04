package com.misha.labam.servlet;

import com.misha.labam.dao.OrderDao;
import com.misha.labam.entity.*;

import com.misha.labam.service.ProductService;
import com.misha.labam.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.SneakyThrows;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private OrderDao orderDao;

    @SneakyThrows
    @Override
    public void init() {
        orderDao = new OrderDao();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserService();
        Long productId = Long.parseLong(req.getParameter("id"));
        String name = req.getUserPrincipal().getName();
        User byEmail = userService.findByEmail(name);
        Product product = new ProductService().getProductById(productId).get();
        Order order = new Order(byEmail, product);
        orderDao.save(order);

        resp.sendRedirect("/products");
    }

    @Override
    @SneakyThrows
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getUserPrincipal().getName();
        User byEmail = new UserService().findByEmail(name);
        List<Order> byUserId = new OrderDao().findByUserId(byEmail.getId());
        req.setAttribute("orders", byUserId);
        req.getRequestDispatcher("/WEB-INF/classes/views/orders.jsp").forward(req, resp);
    }

}
