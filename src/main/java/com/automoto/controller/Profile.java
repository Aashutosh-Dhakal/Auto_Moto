package com.automoto.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;

import com.automoto.model.UserModel;
import com.automoto.service.ProfileService;
import com.automoto.util.ImageUtil;
import com.automoto.util.ValidationUtil;

/**
 * Handles user profile management including viewing, updating profile information,
 * changing passwords, and uploading profile pictures.
 * Supports multipart requests for file uploads with size limitations.
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/profile"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class Profile extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProfileService profileService = new ProfileService();    
    
    /**
     * Handles GET requests to display user profile.
     * Retrieves user details from database and forwards to profile view page.
     * Redirects to login if no user session exists.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = (String) request.getSession().getAttribute("email");
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        UserModel user = profileService.getUserDetails(email);
        if (user != null) {
            request.setAttribute("user", user);
        }
        
        request.getRequestDispatcher("WEB-INF/pages/profile.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for profile updates including:
     * - Password changes
     * - Profile picture uploads
     * - General profile information updates
     * Performs validation before processing any updates.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = (String) request.getSession().getAttribute("email");
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Handle password change request
        if (request.getParameter("oldPassword") != null) {
            handlePasswordChange(request, response, email);
            return;
        }
        
        // Check for multipart file upload
        String contentType = request.getContentType();
        boolean isMultipart = contentType != null && contentType.startsWith("multipart/");
        
        // Handle profile picture upload
        if (isMultipart) {
            try {
                Part filePart = request.getPart("profile-upload");
                if (filePart != null && filePart.getSize() > 0) {
                    handleProfilePictureUpload(request, response, email, filePart);
                    return;
                }
            } catch (Exception e) {
                request.setAttribute("error", "Error processing file upload: " + e.getMessage());
                doGet(request, response);
                return;
            }
        }
        
        // Handle regular profile update
        handleProfileUpdate(request, response, email);
    }
    
    /**
     * Processes profile picture uploads.
     * Validates image type, uploads to server, and updates database record.
     */
    private void handleProfilePictureUpload(HttpServletRequest request, HttpServletResponse response, 
            String email, Part filePart) throws ServletException, IOException {
        ImageUtil imageUtil = new ImageUtil();
        String imageName = imageUtil.getImageNameFromPart(filePart);

        // Validate image type
        if (!imageName.toLowerCase().matches(".*\\.(jpg|jpeg|png|gif)$")) {
            request.setAttribute("error", "Only JPG, JPEG, PNG, or GIF images are allowed");
            doGet(request, response);
            return;
        }
        
        // Upload image to server
        boolean uploadSuccess = imageUtil.uploadImage(filePart, 
            request.getServletContext().getRealPath(""), "profile_images");
        
        if (!uploadSuccess) {
            request.setAttribute("error", "Failed to upload profile picture");
            doGet(request, response);
            return;
        }
        
        // Update database with new image reference
        boolean updateSuccess = profileService.updateProfilePicture(email, imageName);
        if (updateSuccess) {
            request.setAttribute("success", "Profile picture updated successfully");
        } else {
            request.setAttribute("error", "Failed to update profile picture in database");
        }
        
        doGet(request, response);
    }
    
    /**
     * Handles updates to basic profile information.
     * Validates all fields and checks for uniqueness before updating.
     */
    private void handleProfileUpdate(HttpServletRequest request, HttpServletResponse response, 
        String email) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");
        String newEmail = request.getParameter("email");
        String citizenshipNo = request.getParameter("citizenshipNo");
        String licenseNo = request.getParameter("licenseNo");

        // Validate all input fields
        if (ValidationUtil.isNullOrEmpty(firstName) || !ValidationUtil.isAlphabetic(firstName)) {
            request.setAttribute("error", "First name must contain only letters");
            doGet(request, response);
            return;
        }
        
        if (ValidationUtil.isNullOrEmpty(lastName) || !ValidationUtil.isAlphabetic(lastName)) {
            request.setAttribute("error", "Last name must contain only letters");
            doGet(request, response);
            return;
        }
        
        if (!ValidationUtil.isValidPhoneNumber(phoneNumber)) {
            request.setAttribute("error", "Phone number must be 10 digits starting with 98");
            doGet(request, response);
            return;
        }
        
        if (!ValidationUtil.isValidEmail(newEmail)) {
            request.setAttribute("error", "Invalid email format");
            doGet(request, response);
            return;
        }
        
        if (!ValidationUtil.isValidDocument(citizenshipNo)) {
            request.setAttribute("error", "Citizenship number must be 10 digits");
            doGet(request, response);
            return;
        }
        
        if (!ValidationUtil.isValidDocument(licenseNo)) {
            request.setAttribute("error", "License number must be 10 digits");
            doGet(request, response);
            return;
        }

        // Check for duplicate values
        if (profileService.isEmailTaken(newEmail, email)) {
            request.setAttribute("error", "Email is already taken");
            doGet(request, response);
            return;
        }
        
        if (profileService.isPhoneTaken(phoneNumber, email)) {
            request.setAttribute("error", "Phone number is already taken");
            doGet(request, response);
            return;
        }
        
        if (profileService.isCitizenshipNoTaken(citizenshipNo, email)) {
            request.setAttribute("error", "Citizenship number is already taken");
            doGet(request, response);
            return;
        }
        
        if (profileService.isLicenseNoTaken(licenseNo, email)) {
            request.setAttribute("error", "License number is already taken");
            doGet(request, response);
            return;
        }
        
        // Create and update user model
        UserModel userModel = new UserModel();
        userModel.setFirstName(firstName);
        userModel.setLastName(lastName);
        userModel.setPhoneNumber(phoneNumber);
        userModel.setEmail(newEmail);
        userModel.setCitizenshipNo(citizenshipNo);
        userModel.setLicenseNo(licenseNo);
        
        boolean success = profileService.editUser(userModel, email);
        if (success) {
            request.getSession().setAttribute("email", newEmail);
            request.setAttribute("success", "Profile updated successfully");
        } else {
            request.setAttribute("error", "Failed to update profile");
        }
        
        doGet(request, response);
    }
    
    /**
     * Handles password change requests.
     * Verifies current password and validates new password before updating.
     */
    private void handlePasswordChange(HttpServletRequest request, HttpServletResponse response, 
            String email) throws ServletException, IOException {
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // Validate password fields
        if (ValidationUtil.isNullOrEmpty(oldPassword) || 
            ValidationUtil.isNullOrEmpty(newPassword) || 
            ValidationUtil.isNullOrEmpty(confirmPassword)) {
            request.setAttribute("error", "All password fields are required");
            doGet(request, response);
            return;
        }
        
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "New password and confirmation don't match");
            doGet(request, response);
            return;
        }
        
        if (!profileService.verifyCurrentPassword(email, oldPassword)) {
            request.setAttribute("error", "Current password is incorrect");
            doGet(request, response);
            return;
        }
        
        if (!ValidationUtil.isValidPassword(newPassword)) {
            request.setAttribute("error", 
                "New Password should contain Min 8 chars, 1 uppercase, 1 number, 1 special.");
            doGet(request, response);
            return;
        }
        
        // Update password if all validations pass
        boolean success = profileService.updatePassword(email, newPassword);
        if (success) {
            request.setAttribute("success", "Password updated successfully");
        } else {
            request.setAttribute("error", "Failed to update password");
        }
        
        doGet(request, response);
    }
}