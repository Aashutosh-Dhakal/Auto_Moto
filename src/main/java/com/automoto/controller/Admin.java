package com.automoto.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.util.List;

import com.automoto.model.BikeModel;
import com.automoto.model.UserModel;
import com.automoto.service.AdminService;
import com.automoto.util.ImageUtil;
import com.automoto.service.ProfileService;

/**
 * Admin servlet handles all administrative operations including bike and user management.
 * Supports file uploads for bike images and manages CRUD operations for bikes and users.
 * 
 * @WebServlet Maps to "/admin" URL and supports asynchronous operations.
 * @MultipartConfig Configures file upload limits (2MB threshold, 10MB max file, 50MB max request).
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/admin"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class Admin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ImageUtil imageUtil = new ImageUtil();
    private ProfileService profileService = new ProfileService();

    /**
     * Handles HTTP GET requests by delegating to processRequest.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles HTTP POST requests by delegating to processRequest.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Central request processor for both GET and POST requests.
     * Loads profile data, handles actions, and prepares admin dashboard data.
     * 
     * @param request  The HTTP request object.
     * @param response The HTTP response object.
     * @throws ServletException If servlet processing fails.
     * @throws IOException      If I/O operations fail.
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Load user profile data for the admin dashboard
        loadProfileData(request);
        
        // Set active tab (defaults to "home" if not specified)
        String activeTab = request.getParameter("activeTab");
        if (activeTab != null) {
            request.setAttribute("activeTab", activeTab);
        } else if (request.getAttribute("activeTab") == null) {
            request.setAttribute("activeTab", "home");
        }
        
        // Process POST actions (add/update/delete operations)
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            handlePostActions(request);
        }
        
        // Load all bikes and users for admin view
        AdminService adminService = new AdminService();
        if (adminService.isConnectionError()) {
            request.setAttribute("errorMessage", "Database connection error!");
        } else {
            List<BikeModel> bikes = adminService.getAllBikes();
            List<UserModel> users = adminService.getAllUsers();
            
            request.setAttribute("bikes", bikes);
            request.setAttribute("users", users);
        }
        adminService.closeConnection();
        
        // Forward to admin JSP page
        request.getRequestDispatcher("WEB-INF/pages/admin.jsp").forward(request, response);
    }

    /**
     * Loads profile data of the logged-in admin user.
     * 
     * @param request The HTTP request object containing session data.
     */
    private void loadProfileData(HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        if (email == null) return;
        
        UserModel user = profileService.getUserDetails(email);
        if (user != null) {
            request.setAttribute("user", user);
        }
    }

    /**
     * Handles POST actions (CRUD operations for bikes and users).
     * 
     * @param request The HTTP request object containing action parameters.
     * @throws IOException      If I/O operations fail.
     * @throws ServletException If servlet processing fails.
     */
    private void handlePostActions(HttpServletRequest request) 
            throws IOException, ServletException {
        String action = request.getParameter("action");
        if (action == null) return;
        
        AdminService adminService = new AdminService();
        try {
            switch (action) {
                case "addBike":
                    handleAddBike(request, adminService);
                    break;
                case "updateBike":
                    handleUpdateBike(request, adminService);
                    break;
                case "deleteBike":
                    handleDeleteBike(request, adminService);
                    break;
                case "deleteUser":
                    handleDeleteUser(request, adminService);
                    break;
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error processing " + action + ": " + e.getMessage());
        } finally {
            adminService.closeConnection();
        }
    }

    /**
     * Handles bike addition with image upload.
     */
    private void handleAddBike(HttpServletRequest request, AdminService adminService) 
            throws IOException, ServletException {
        BikeModel bike = createBikeFromRequest(request);
        boolean success = adminService.addBike(bike);
        request.setAttribute(success ? "successMessage" : "errorMessage", 
            success ? "Bike added successfully!" : "Failed to add bike. Plate number might already exist.");
    }

    /**
     * Handles bike updates with optional image upload.
     */
    private void handleUpdateBike(HttpServletRequest request, AdminService adminService) 
            throws IOException, ServletException {
        BikeModel bike = createBikeFromRequest(request);
        boolean success = adminService.updateBike(bike);
        request.setAttribute(success ? "successMessage" : "errorMessage", 
            success ? "Bike updated successfully!" : "Failed to update bike.");
    }

    /**
     * Creates a BikeModel from request parameters, including image upload handling.
     */
    private BikeModel createBikeFromRequest(HttpServletRequest request) 
            throws IOException, ServletException {
        BikeModel bike = new BikeModel();
        bike.setPlateNo(request.getParameter("plateNo"));
        bike.setBrand(request.getParameter("brand"));
        bike.setModel(request.getParameter("model"));
        bike.setType(request.getParameter("type"));
        bike.setBikeCondition(request.getParameter("condition"));
        
        // Handle image upload if present
        Part filePart = request.getPart("imageFile");
        if (filePart != null && filePart.getSize() > 0 && 
            imageUtil.uploadImage(filePart, request.getServletContext().getRealPath(""), "bikes")) {
            bike.setImage(imageUtil.getImageNameFromPart(filePart));
        }
        return bike;
    }

    /**
     * Handles bike deletion by plate number.
     */
    private void handleDeleteBike(HttpServletRequest request, AdminService adminService) {
        String plateNo = request.getParameter("plateNo");
        boolean success = adminService.deleteBike(plateNo);
        request.setAttribute(success ? "successMessage" : "errorMessage", 
            success ? "Bike deleted successfully!" : "Failed to delete bike.");
    }

    /**
     * Handles user deletion by email.
     */
    private void handleDeleteUser(HttpServletRequest request, AdminService adminService) {
        String email = request.getParameter("email");
        boolean success = adminService.deleteUser(email);
        request.setAttribute(success ? "successMessage" : "errorMessage", 
            success ? "User deleted successfully!" : "Failed to delete user.");
    }
}