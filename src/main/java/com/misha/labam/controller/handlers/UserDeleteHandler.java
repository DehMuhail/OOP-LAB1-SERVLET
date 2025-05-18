package com.misha.labam.controller.handlers;

import com.misha.labam.controller.HttpHandlerBase;
import com.misha.labam.security.JwtUtil;
import com.misha.labam.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDeleteHandler extends HttpHandlerBase {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final Pattern idPattern;

    public UserDeleteHandler(UserService userService) {
        this.userService = userService;
        this.jwtUtil = new JwtUtil();
        this.idPattern = Pattern.compile("^/deleteUser/(\\d+)$");
        addPath("^/deleteUser/\\d+$");
    }

    @Override
    protected String doGet(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> model) throws ServletException, IOException {
        String path = req.getPathInfo();
        Matcher matcher = idPattern.matcher(path);
        
        if (matcher.matches()) {
            String id = matcher.group(1);
            
            try {
                long userId = Long.parseLong(id);
                userService.deleteUser(userId);
                
                // Clear the access token cookie
                Cookie cookie = new Cookie("accessToken", "");
                cookie.setMaxAge(0);
                cookie.setPath("/");
                resp.addCookie(cookie);
                
                return "/@redirect";
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
                return null;
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required");
            return null;
        }
    }
}