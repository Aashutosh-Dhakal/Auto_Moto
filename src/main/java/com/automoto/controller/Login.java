package com.automoto.controller;

import com.automoto.model.UserModel;
import com.automoto.service.LoginService;
import com.automoto.util.CookieUtil;
import com.automoto.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles user authentication and login processes.
 * Manages both GET requests (login page display) and POST requests (login attempts).
 * Supports session management and cookie handling for user roles.
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/login"})
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final LoginService loginService = new LoginService();

    /**
     * Handles GET requests for the login page.
     * Displays login form and shows success message if redirected after registration.
     * 
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        if (request.getParameter("registered") != null && request.getParameter("registered").equals("true")) {
            request.setAttribute("successMessage", "User successfully registered! Proceed to login!");
        }
        request.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for user login attempts.
     * Validates credentials, manages sessions, and handles authentication results.
     * 
     * @param request  The HttpServletRequest object containing login parameters
     * @param response The HttpServletResponse object for redirection
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // Validate input fields
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            if (email == null || email.trim().isEmpty()) {
                request.setAttribute("emailError", "Email is required");
            }
            if (password == null || password.trim().isEmpty()) {
                request.setAttribute("passwordError", "Password is required");
            }
            request.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(request, response);
            return;
        }
        
        UserModel user = new UserModel();
        user.setEmail(email);
        user.setPassword(password);
        
        try {
            Boolean loginResult = loginService.loginUser(user);
            
            if (loginResult == null) {
                request.setAttribute("errorMessage", "System error occurred. Please try again later.");
                request.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(request, response);
            } else if (loginResult) {
                // Successful login handling
                SessionUtil.setAttribute(request, "email", email);
                
                String userRole = loginService.getUserRole(email);
                
                CookieUtil.addCookie(response, "role", userRole.toLowerCase(), 10 *  30);
                
                SessionUtil.setAttribute(request, "successMessage", "Login successful! Welcome back!");
                
                response.sendRedirect(request.getContextPath() + "/home");
            } else {
                // Failed login handling
                request.setAttribute("errorMessage", "Invalid email or password");
                request.setAttribute("email", email);
                request.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "System error occurred. Please try again later.");
            request.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(request, response);
        }
    }
}