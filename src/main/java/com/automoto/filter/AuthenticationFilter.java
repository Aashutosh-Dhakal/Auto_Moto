package com.automoto.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
//import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.automoto.util.CookieUtil;

@WebFilter(asyncSupported = true, urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

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

//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        
        // Allow access to resources
        if (uri.endsWith(".png") || uri.endsWith(".jpg") || uri.endsWith(".jpeg") || 
        	    uri.endsWith(".css") || uri.endsWith(".gif")) {
        	    chain.doFilter(request, response);
        	    return;
        	}
        
        String userRole = CookieUtil.getCookie(req, "role") != null ? CookieUtil.getCookie(req, "role").getValue()
                : null;

        if ("admin".equals(userRole)) {
            // Admin is logged in
            if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
                res.sendRedirect(req.getContextPath() + PROFILE);
            } else if (uri.endsWith(ADMIN) || uri.endsWith(HOME) || uri.endsWith(ROOT) 
                    || uri.endsWith(ABOUT) || uri.endsWith(CONTACT) || uri.endsWith(PROFILE) 
                    || uri.endsWith(LOGOUT) || uri.endsWith(SEARCH)) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(req.getContextPath() + ADMIN);
            }
        } else if ("customer".equals(userRole)) {
            // User is logged in
            if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
                res.sendRedirect(req.getContextPath() + PROFILE);
            } else if (uri.endsWith(HOME) || uri.endsWith(ROOT) || uri.endsWith(ABOUT) 
                    || uri.endsWith(CONTACT) || uri.endsWith(PROFILE) || uri.endsWith(LOGOUT) 
                    || uri.endsWith(SEARCH)) {
                chain.doFilter(request, response);
            } else if (uri.endsWith(ADMIN)) {
                res.sendRedirect(req.getContextPath() + HOME);
            } else {
                res.sendRedirect(req.getContextPath() + HOME);
            }
        } else {
            // Not logged in
            if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER) || uri.endsWith(HOME) 
                    || uri.endsWith(ROOT) || uri.endsWith(ABOUT) || uri.endsWith(CONTACT) || uri.endsWith(SEARCH)) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(req.getContextPath() + LOGIN);
            }
        }
    }

//    @Override
//    public void destroy() {
//    }
}