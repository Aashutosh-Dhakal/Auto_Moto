package com.automoto.controller;

import com.automoto.model.BikeModel;
import com.automoto.service.AdminService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Handles bike search functionality for the AutoMoto application.
 * Supports both GET and POST requests to search bikes by brand, model, or type.
 * Displays all bikes when no search query is provided.
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/search"})
public class SearchBike extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * Handles GET requests for bike search.
     * Processes search query and returns matching bikes or all bikes if no query.
     * 
     * @param request The HttpServletRequest containing search parameters
     * @param response The HttpServletResponse for rendering results
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchQuery = request.getParameter("search");
        AdminService adminService = new AdminService();
        List<BikeModel> bikeList;
        
        if (searchQuery != null && !searchQuery.isEmpty()) {
            // Search bikes based on the query
            bikeList = searchBikes(adminService, searchQuery);
        } else {
            // Get all bikes if no search query
            bikeList = adminService.getAllBikes();
        }
        
        request.setAttribute("bikeList", bikeList);
        request.setAttribute("searchQuery", searchQuery);
        request.getRequestDispatcher("WEB-INF/pages/search.jsp").forward(request, response);
    }

    /**
     * Handles POST requests by delegating to doGet().
     * Ensures consistent behavior for both GET and POST requests.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    
    /**
     * Filters bikes based on search query matching brand, model, or type.
     * Performs case-insensitive comparison against all bike fields.
     * 
     * @param adminService The AdminService instance for bike data access
     * @param searchQuery The search term to match against bike properties
     * @return List of BikeModel objects that match the search criteria
     */
    private List<BikeModel> searchBikes(AdminService adminService, String searchQuery) {
        List<BikeModel> allBikes = adminService.getAllBikes();
        String queryLower = searchQuery.toLowerCase();
        
        // Filter bikes based on search query (brand, model, or type)
        return allBikes.stream()
            .filter(bike -> bike.getBrand().toLowerCase().contains(queryLower) ||
                            bike.getModel().toLowerCase().contains(queryLower) ||
                            bike.getType().toLowerCase().contains(queryLower))
            .toList();
    }
}