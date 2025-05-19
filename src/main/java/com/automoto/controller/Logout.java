package com.automoto.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.automoto.util.CookieUtil;
import com.automoto.util.SessionUtil;

/**
 * Handles user logout functionality by clearing session and role cookie.
 * Supports both GET and POST requests for logout operations.
 * Redirects to home page after successful logout.
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/logout"})
public class Logout extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests for logout.
     * Delegates to performLogout method to process the logout operation.
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        performLogout(request, response);
    }
    
    /**
     * Handles POST requests for logout.
     * Delegates to performLogout method to process the logout operation.
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        performLogout(request, response);
    }

    /**
     * Performs the actual logout operations:
     * 1. Deletes the role cookie
     * 2. Invalidates the current session
     * 3. Redirects to home page
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws IOException if an I/O error occurs during redirection
     */
    private void performLogout(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        CookieUtil.deleteCookie(response, "role");

        SessionUtil.invalidateSession(request);

        response.sendRedirect(request.getContextPath() + "/home");
    }
}