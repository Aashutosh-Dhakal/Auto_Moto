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
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Always load profile data first
        loadProfileData(request);
        
        // Set active tab from request parameter if available
        String activeTab = request.getParameter("activeTab");
        if (activeTab != null) {
            request.setAttribute("activeTab", activeTab);
        } else if (request.getAttribute("activeTab") == null) {
            request.setAttribute("activeTab", "home");
        }
        
        // Handle POST actions if present
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            handlePostActions(request);
        }
        
        // Load admin data
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
        
        // Forward to JSP
        request.getRequestDispatcher("WEB-INF/pages/admin.jsp").forward(request, response);
    }
    
    private void loadProfileData(HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        if (email == null) {
            return;
        }
        UserModel user = profileService.getUserDetails(email);
        if (user != null) {
            request.setAttribute("user", user);
        }
    }
    
    private void handlePostActions(HttpServletRequest request) throws IOException, ServletException {
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
    
    private void handleAddBike(HttpServletRequest request, AdminService adminService) 
            throws IOException, ServletException {
        BikeModel bike = createBikeFromRequest(request);
        boolean success = adminService.addBike(bike);
        request.setAttribute(success ? "successMessage" : "errorMessage", 
            success ? "Bike added successfully!" : "Failed to add bike. Plate number might already exist.");
    }

    private void handleUpdateBike(HttpServletRequest request, AdminService adminService) 
            throws IOException, ServletException {
        BikeModel bike = createBikeFromRequest(request);
        boolean success = adminService.updateBike(bike);
        request.setAttribute(success ? "successMessage" : "errorMessage", 
            success ? "Bike updated successfully!" : "Failed to update bike.");
    }
    
    private BikeModel createBikeFromRequest(HttpServletRequest request) throws IOException, ServletException {
        BikeModel bike = new BikeModel();
        bike.setPlateNo(request.getParameter("plateNo"));
        bike.setBrand(request.getParameter("brand"));
        bike.setModel(request.getParameter("model"));
        bike.setType(request.getParameter("type"));
        bike.setBikeCondition(request.getParameter("condition"));
        
        Part filePart = request.getPart("imageFile");
        if (filePart != null && filePart.getSize() > 0 && imageUtil.uploadImage(filePart, 
            request.getServletContext().getRealPath(""), "bikes")) {
            bike.setImage(imageUtil.getImageNameFromPart(filePart));
        }
        return bike;
    }
    
    private void handleDeleteBike(HttpServletRequest request, AdminService adminService) {
        String plateNo = request.getParameter("plateNo");
        boolean success = adminService.deleteBike(plateNo);
        request.setAttribute(success ? "successMessage" : "errorMessage", 
            success ? "Bike deleted successfully!" : "Failed to delete bike.");
    }
    
    private void handleDeleteUser(HttpServletRequest request, AdminService adminService) {
        String email = request.getParameter("email");
        boolean success = adminService.deleteUser(email);
        request.setAttribute(success ? "successMessage" : "errorMessage", 
            success ? "User deleted successfully!" : "Failed to delete user.");
    }
}