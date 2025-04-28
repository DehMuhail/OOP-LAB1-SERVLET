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

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private final JwtUtil jwtUtil = new JwtUtil();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        Cookie cookie1 = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("accessToken")).findFirst().get();
        String email = jwtUtil.getUsername(cookie1.getValue());
        User user = userService.findByEmail(email);
        req.setAttribute("user", user);
        if (user != null) {
            req.getRequestDispatcher("/WEB-INF/classes/views/profile.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        }
    }
}
