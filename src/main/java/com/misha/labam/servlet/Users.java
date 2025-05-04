package com.misha.labam.servlet;

import com.misha.labam.entity.Role;
import com.misha.labam.entity.User;
import com.misha.labam.security.JwtUtil;
import com.misha.labam.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class Users extends HttpServlet {

    private final UserService userService = new UserService();
    private final JwtUtil jwtUtil = new JwtUtil();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getUserPrincipal().getName();
        User byEmail = userService.findByEmail(name);
        if (!byEmail.getRole().equals(Role.ADMIN)) {
            List<User> all = userService.getAllUsers();
            System.out.println(all);
            req.setAttribute("users", all);
            req.setAttribute("admin", false);
            req.getRequestDispatcher("/WEB-INF/classes/views/users.jsp").forward(req, resp);
        } else {
            List<User> all = userService.getAllUsers();
            System.out.println(all);
            req.setAttribute("users", all);
            req.setAttribute("admin", true);
            req.getRequestDispatcher("/WEB-INF/classes/views/users.jsp").forward(req, resp);
        }
    }
}
