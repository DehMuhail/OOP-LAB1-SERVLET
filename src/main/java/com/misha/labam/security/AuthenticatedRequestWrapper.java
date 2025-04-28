package com.misha.labam.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.security.Principal;

public class AuthenticatedRequestWrapper extends HttpServletRequestWrapper {
    private final String username;

    public AuthenticatedRequestWrapper(HttpServletRequest request, String username) {
        super(request);
        this.username = username;
    }

    @Override
    public Principal getUserPrincipal() {
        return () -> username;
    }
}
