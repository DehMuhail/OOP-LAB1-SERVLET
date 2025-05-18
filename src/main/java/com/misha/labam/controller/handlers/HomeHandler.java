package com.misha.labam.controller.handlers;

import com.misha.labam.controller.HttpHandlerBase;
import com.misha.labam.entity.User;
import com.misha.labam.security.JwtUtil;
import com.misha.labam.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class HomeHandler extends HttpHandlerBase {
    
    private final UserService userService;
    private final JwtUtil jwtUtil;
    
    public HomeHandler(UserService userService) {
        this.userService = userService;
        this.jwtUtil = new JwtUtil();
        addPath("^/$");
    }
    
    @Override
    protected String doGet(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> model) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            Cookie cookie1 = Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals("accessToken"))
                    .findFirst()
                    .orElse(null);
            
            if (cookie1 != null) {
                User byEmail = userService.findByEmail(jwtUtil.getUsername(cookie1.getValue()));
                model.put("user", byEmail);
            }
        }
        return "home";
    }
}