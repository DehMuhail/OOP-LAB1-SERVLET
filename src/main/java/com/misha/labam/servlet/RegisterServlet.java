package com.misha.labam.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.misha.labam.dto.LoginDto;
import com.misha.labam.entity.User;
import com.misha.labam.security.JwtUtil;
import com.misha.labam.service.UserService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration")
public class RegisterServlet extends HttpServlet {
    @Inject
    private UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JwtUtil jwtUtil = new JwtUtil();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/classes/views/registration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserService userService1 = new UserService();
        User save = userService1.save(new LoginDto(email, password));
        Cookie cookie = new Cookie("accessToken", jwtUtil.generateToken(save));
        cookie.setHttpOnly(true);
        cookie.setMaxAge(24000000);
        cookie.setPath("/");
        resp.addCookie(cookie);
        resp.setContentType("application/json");
        resp.getWriter().write("{\"message\": \"User registered successfully\"}");
        resp.sendRedirect(req.getContextPath() + "/profile");
    }
}

