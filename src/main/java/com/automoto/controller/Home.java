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

@WebServlet(asyncSupported = true, urlPatterns = {"/home", "/"})
public class Home extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Transfer messages from session to request before processing
        transferSessionMessagesToRequest(request);
        
        AdminService adminService = new AdminService();
        List<BikeModel> bikeList = adminService.getAllBikes();
        request.setAttribute("bikeList", bikeList);
        request.getRequestDispatcher("WEB-INF/pages/home.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("rentBike".equals(action)) {
            handleRentBike(request, response);
        } else {
            doGet(request, response);
        }
    }
    
    private void handleRentBike(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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