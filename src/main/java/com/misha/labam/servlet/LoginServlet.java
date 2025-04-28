package com.misha.labam.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.misha.labam.entity.User;
import com.misha.labam.security.JwtUtil;
import com.misha.labam.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final  JwtUtil jwtUtil = new JwtUtil();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/classes/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String email = req.getParameter("email");
       String password = req.getParameter("password");
        User user;
        UserService userService = new UserService();
       try {
            user = userService.authenticate(email, password);
       }catch (RuntimeException e){
           resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
           return;
       }

        if (user != null) {
            String token = jwtUtil.generateToken(user);
            resp.setContentType("application/json");
            Cookie cookie = new Cookie("accessToken", token);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(24000000);
            cookie.setPath("/");
            resp.addCookie(cookie);
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
        }
    }
}