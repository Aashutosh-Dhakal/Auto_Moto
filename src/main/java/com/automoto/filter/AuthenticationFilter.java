package com.automoto.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.automoto.util.CookieUtil;

/**
 * Authentication filter that controls access to application routes based on user roles.
 * Implements role-based access control (RBAC) for admin and customer users.
 * Redirects unauthenticated users to login page when trying to access protected resources.
 */
@WebFilter(asyncSupported = true, urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

    // Constants for application paths
    private static final String LOGIN = "/login";
    private static final String REGISTER = "/register";
    private static final String HOME = "/home";
    private static final String ROOT = "/";
    private static final String ADMIN = "/admin";
    private static final String ABOUT = "/about";
    private static final String CONTACT = "/contact";
    private static final String PROFILE = "/profile";
    private static final String LOGOUT = "/logout";
    private static final String SEARCH = "/search";

    /**
     * Main filter method that processes each request and applies access control rules.
     * 
     * @param request The ServletRequest object
     * @param response The ServletResponse object
     * @param chain The FilterChain for proceeding with the request
     * @throws IOException if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        
        // Allow direct access to static resources
        if (uri.endsWith(".png") || uri.endsWith(".jpg") || uri.endsWith(".jpeg") || 
            uri.endsWith(".css") || uri.endsWith(".gif")) {
            chain.doFilter(request, response);
            return;
        }
        
        // Get user role from cookie
        String userRole = CookieUtil.getCookie(req, "role") != null ? 
                         CookieUtil.getCookie(req, "role").getValue() : null;

        // Admin user access control
        if ("admin".equals(userRole)) {
            // Prevent access to login/register for logged-in admin
            if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
                res.sendRedirect(req.getContextPath() + PROFILE);
            } 
            // Allow access to admin-specific and common routes
            else if (uri.endsWith(ADMIN) || uri.endsWith(HOME) || uri.endsWith(ROOT) 
                    || uri.endsWith(ABOUT) || uri.endsWith(CONTACT) || uri.endsWith(PROFILE) 
                    || uri.endsWith(LOGOUT) || uri.endsWith(SEARCH)) {
                chain.doFilter(request, response);
            } 
            // Redirect to admin dashboard for any other route
            else {
                res.sendRedirect(req.getContextPath() + ADMIN);
            }
        } 
        // Customer user access control
        else if ("customer".equals(userRole)) {
            // Prevent access to login/register for logged-in customer
            if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
                res.sendRedirect(req.getContextPath() + PROFILE);
            } 
            // Allow access to customer-accessible routes
            else if (uri.endsWith(HOME) || uri.endsWith(ROOT) || uri.endsWith(ABOUT) 
                    || uri.endsWith(CONTACT) || uri.endsWith(PROFILE) || uri.endsWith(LOGOUT) 
                    || uri.endsWith(SEARCH)) {
                chain.doFilter(request, response);
            } 
            // Prevent customer access to admin routes
            else if (uri.endsWith(ADMIN)) {
                res.sendRedirect(req.getContextPath() + HOME);
            } 
            // Default redirect for customers
            else {
                res.sendRedirect(req.getContextPath() + HOME);
            }
        } 
        // Unauthenticated user access control
        else {
            // Allow access to public routes only
            if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER) || uri.endsWith(HOME) 
                    || uri.endsWith(ROOT) || uri.endsWith(ABOUT) || uri.endsWith(CONTACT) 
                    || uri.endsWith(SEARCH)) {
                chain.doFilter(request, response);
            } 
            // Redirect to login for protected routes
            else {
                res.sendRedirect(req.getContextPath() + LOGIN);
            }
        }
    }
}