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

public class ProfileHandler extends HttpHandlerBase {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public ProfileHandler(UserService userService) {
        this.userService = userService;
        this.jwtUtil = new JwtUtil();
        addPath("^/profile$");
    }

    @Override
    protected String doGet(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> model) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No cookies found");
            return null;
        }
        
        Cookie cookie1 = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("accessToken"))
                .findFirst()
                .orElse(null);
                
        if (cookie1 == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No access token found");
            return null;
        }
        
        String email = jwtUtil.getUsername(cookie1.getValue());
        User user = userService.findByEmail(email);
        
        if (user != null) {
            model.put("user", user);
            return "profile";
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return null;
        }
    }
}