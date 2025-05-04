package com.misha.labam.servlet;

import com.misha.labam.dto.LoginDto;
import com.misha.labam.entity.User;
import com.misha.labam.security.JwtUtil;
import com.misha.labam.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/userAdmin")
public class UserAdmin extends HttpServlet {

    private final UserService userService = new UserService();
    private final JwtUtil jwtUtil = new JwtUtil();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User byEmail = userService.register(new LoginDto("admin@admin","admin"));
    }
}
