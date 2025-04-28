package com.misha.labam.servlet;

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
import java.util.Arrays;

@WebServlet("/")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        Cookie cookie1 = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("accessToken")).findFirst().orElse(null);
        UserService userService = new UserService();
        JwtUtil jwtUtil = new JwtUtil();
        if (cookie1 != null) {

            User byEmail = userService.findByEmail(jwtUtil.getUsername(cookie1.getValue()));
            System.out.println(byEmail);
            req.setAttribute("user", byEmail);
        }
        req.getRequestDispatcher("/WEB-INF/classes/views/home.jsp").forward(req, resp);
    }
}