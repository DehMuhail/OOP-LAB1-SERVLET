package com.misha.labam.routing;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.misha.labam.controller.HttpHandler;
import com.misha.labam.controller.handlers.*;
import com.misha.labam.service.ProductService;
import com.misha.labam.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(value = "/*", loadOnStartup = 1)
public class Controller extends HttpServlet {

    private UserService userService;
    private ProductService productService;

    List<HttpHandler> handlers;

    @Override
    public void init() throws ServletException {
        initServices();
        registerHandlers();
    }

    private void initServices() {
        userService = new UserService();
        productService = new ProductService();
    }

    protected void registerHandlers() {
        handlers = new ArrayList<>();
        // Register all handlers
        handlers.add(new HomeHandler(userService));
        handlers.add(new ProductsHandler(productService, userService));
        handlers.add(new ProductDeleteHandler(productService, userService));
        handlers.add(new ProfileHandler(userService));
        handlers.add(new RegisterHandler(userService));
        handlers.add(new UserDeleteHandler(userService));
        handlers.add(new UsersHandler(userService));
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getRequestURI();
        // Remove context path if present
        String contextPath = req.getContextPath();
        if (contextPath != null && !contextPath.isEmpty() && path.startsWith(contextPath)) {
            path = path.substring(contextPath.length());
        }
        if (path == null || path.isEmpty()) {
            path = "/";
        }

        Map<String, Object> model = new HashMap<>();

        for (HttpHandler handler : handlers) {
            if (handler.isProcessingPath(path)) {
                // Delegate work to the handler
                String view = handler.handle(req, resp, model);

                // Put model objects as attributes
                mapModelToRequest(model, req);

                // Check if the view is a redirect
                if (view.endsWith("@redirect")) {
                    view = view.substring(0, view.lastIndexOf("@redirect"));
                    resp.sendRedirect(view);

                } else {
                    log("sad");
                    req.getRequestDispatcher(String.format("/WEB-INF/views/%s.jsp", view)).forward(req, resp);

                }
                break;
            }
        }

        // If no handler was found
        if (!resp.isCommitted()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found");
        }
    }

    private void mapModelToRequest(Map<String, Object> model, HttpServletRequest request) {
        for (Map.Entry<String, Object> entry : model.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
    }
}
