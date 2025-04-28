package com.misha.labam.servlet;

import com.misha.labam.security.JwtUtil;
import com.misha.labam.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/deleteUser/*")
public class UserDelete extends HttpServlet {

    private final UserService userService = new UserService();
    private final JwtUtil jwtUtil = new JwtUtil();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo();
        if (pathInfo != null && !pathInfo.isEmpty()) {
            String id = pathInfo.substring(1);  // Remove the first slash, e.g., "123"

            try {
                long userId = Long.parseLong(id);
                userService.deleteUser(userId);
                Cookie cookie = new Cookie("accessToken", "");
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
                resp.sendRedirect("/");
//                resp.getWriter().write("User " + userId + " deleted successfully!");
            } catch (NumberFormatException e) {
                resp.getWriter().write("Invalid ID format");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            resp.getWriter().write("User ID is required");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
