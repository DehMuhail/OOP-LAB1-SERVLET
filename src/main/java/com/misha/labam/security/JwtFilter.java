package com.misha.labam.security;

import com.misha.labam.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class JwtFilter implements Filter {
    private JwtUtil jwtUtil;
    private UserService userService;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.jwtUtil = new JwtUtil();
        this.userService = new UserService();
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getRequestURI();
        if (path.startsWith("/login") || path.startsWith("/registration") || path.equals("/") || path.equals("/logout")) {
                chain.doFilter(request, response);
            return;
        }

        String jwt = getTokenFromRequest(req);

        if (jwt != null && !jwt.isBlank() && jwtUtil.validateToken(jwt)) {
            String username = jwtUtil.getUsername(jwt);
            if (!userService.exists(username)) {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User " + username + " does not exist");
                return;
            }

            req = new AuthenticatedRequestWrapper(req, username);
            chain.doFilter(req, response);
        } else {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT not valid");
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }
}
