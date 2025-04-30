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

@WebServlet(asyncSupported = true, urlPatterns = {"/login"})
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final LoginService loginService = new LoginService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        if (request.getParameter("registered") != null && request.getParameter("registered").equals("true")) {
            request.setAttribute("successMessage", "User successfully registered! Proceed to login!");
        }
        request.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
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
                SessionUtil.setAttribute(request, "username", email);
                
                String userRole = loginService.getUserRole(email);
                
                CookieUtil.addCookie(response, "role", userRole.toLowerCase(), 24 * 60 * 60);
                
                SessionUtil.setAttribute(request, "successMessage", "Login successful! Welcome back!");
                
                response.sendRedirect(request.getContextPath() + "/home");
            } else {
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