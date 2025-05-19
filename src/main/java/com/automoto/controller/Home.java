package com.automoto.controller;

import com.automoto.model.BikeModel;
import com.automoto.service.AdminService;
import com.automoto.service.RentalService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Home servlet handles the main landing page and bike rental functionality.
 * Maps to both "/home" and "/" URLs with asynchronous support.
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/home", "/"})
public class Home extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests for the home page.
     * Retrieves all bikes from database and forwards to home.jsp.
     * 
     * @param request The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Transfer messages from session to request before processing
        transferSessionMessagesToRequest(request);
        
        AdminService adminService = new AdminService();
        List<BikeModel> bikeList = adminService.getAllBikes();
        request.setAttribute("bikeList", bikeList);
        request.getRequestDispatcher("WEB-INF/pages/home.jsp").forward(request, response);
    }

    /**
     * Handles POST requests, primarily for bike rental operations.
     * Delegates to GET handler for non-rental actions.
     * 
     * @param request The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("rentBike".equals(action)) {
            handleRentBike(request, response);
        } else {
            doGet(request, response);
        }
    }
    
    /**
     * Processes bike rental requests.
     * Validates user session and bike plate number before attempting rental.
     * 
     * @param request The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws IOException if an I/O error occurs during redirection
     */
    private void handleRentBike(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = (String) request.getSession().getAttribute("email");
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String plateNo = request.getParameter("plateNo");
        if (plateNo == null || plateNo.isEmpty()) {
            request.getSession().setAttribute("errorMessage", "Invalid bike plate number");
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        
        RentalService rentalService = new RentalService();
        boolean success = rentalService.rentBike(email, plateNo);
        if (success) {
            request.getSession().setAttribute("successMessage", "Bike Rented Successfully");
        } else {
            request.getSession().setAttribute("errorMessage", "Failed to rent bike");
        }
        
        response.sendRedirect(request.getContextPath() + "/home");
    }
    
    /**
     * Transfers session messages (success/error) to request attributes.
     * Clears messages from session after transfer.
     * 
     * @param request The HttpServletRequest object
     */
    private void transferSessionMessagesToRequest(HttpServletRequest request) {
        // Transfer success message
        Object successMessage = request.getSession().getAttribute("successMessage");
        if (successMessage != null) {
            request.setAttribute("successMessage", successMessage);
            request.getSession().removeAttribute("successMessage");
        }
        
        // Transfer error message
        Object errorMessage = request.getSession().getAttribute("errorMessage");
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            request.getSession().removeAttribute("errorMessage");
        }
    }
}