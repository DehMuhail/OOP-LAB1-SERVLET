package com.misha.labam.servlet;

import com.misha.labam.dao.OrderDao;
import com.misha.labam.entity.User;
import com.misha.labam.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

@WebServlet("/orderdelete")
public class OrderDelete  extends HttpServlet {
    @Override
    @SneakyThrows
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getUserPrincipal().getName();
        OrderDao orderDao = new OrderDao();
        User byEmail = new UserService().findByEmail(name);
        if(req.getParameter("id")!=null && req.getParameter("id").isEmpty()){
            String id = req.getParameter("id");
            Long idl = Long.parseLong(id);
            orderDao.deleteById(idl,byEmail.getId());
            resp.sendRedirect("/order");
        }else {
            orderDao.deleteAll(byEmail.getId());
            resp.sendRedirect("/products");
        }
    }
}
