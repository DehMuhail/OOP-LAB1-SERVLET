package com.misha.labam.controller.handlers;

import com.misha.labam.controller.HttpHandlerBase;
import com.misha.labam.entity.Role;
import com.misha.labam.entity.User;
import com.misha.labam.security.JwtUtil;
import com.misha.labam.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class UsersHandler extends HttpHandlerBase {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UsersHandler(UserService userService) {
        this.userService = userService;
        this.jwtUtil = new JwtUtil();
        addPath("^/users$");
    }

    @Override
    protected String doGet(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> model) throws ServletException, IOException {
        try {
            String name = req.getUserPrincipal().getName();
            User byEmail = userService.findByEmail(name);
            
            List<User> allUsers = userService.getAllUsers();
            model.put("users", allUsers);
            
            // Set admin flag based on user role
            boolean isAdmin = byEmail.getRole().equals(Role.ADMIN);
            model.put("admin", isAdmin);
            
            return "users";
        } catch (RuntimeException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied: " + e.getMessage());
            return null;
        }
    }
}