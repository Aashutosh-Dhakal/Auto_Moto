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

@WebServlet(asyncSupported = true, urlPatterns = {"/search"})
public class SearchBike extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    
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