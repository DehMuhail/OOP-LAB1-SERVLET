package com.misha.labam.controller.handlers;

import com.misha.labam.controller.HttpHandlerBase;
import com.misha.labam.dto.LoginDto;
import com.misha.labam.entity.User;
import com.misha.labam.security.JwtUtil;
import com.misha.labam.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public class RegisterHandler extends HttpHandlerBase {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public RegisterHandler(UserService userService) {
        this.userService = userService;
        this.jwtUtil = new JwtUtil();
        addPath("^/registration$");
    }

    @Override
    protected String doGet(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> model) throws ServletException, IOException {
        return "registration";
    }

    @Override
    protected String doPost(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> model) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User save = userService.register(new LoginDto(email, password));
        
        Cookie cookie = new Cookie("accessToken", jwtUtil.generateToken(save));
        cookie.setHttpOnly(true);
        cookie.setMaxAge(24000000);
        cookie.setPath("/");
        resp.addCookie(cookie);
        
        resp.setContentType("application/json");
        resp.getWriter().write("{\"message\": \"User registered successfully\"}");
        
        return "/profile@redirect";
    }
}