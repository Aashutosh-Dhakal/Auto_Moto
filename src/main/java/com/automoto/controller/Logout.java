package com.automoto.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.automoto.util.CookieUtil;
import com.automoto.util.SessionUtil;

@WebServlet(asyncSupported = true, urlPatterns = {"/logout"})
public class Logout extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Handle POST requests (form submissions)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        performLogout(request, response);
    }

    // Shared logout logic
    private void performLogout(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        // 1. Delete all authentication cookies
        CookieUtil.deleteCookie(response, "role");
        // Add any other cookies you need to clear
        // CookieUtil.deleteCookie(response, "otherCookieName");

        // 2. Invalidate the session
        SessionUtil.invalidateSession(request);

        // 3. Redirect to home page
        response.sendRedirect(request.getContextPath() + "/home");
    }
}