package com.automoto.controller;

import com.automoto.model.UserModel;
import com.automoto.service.RegisterService;
import com.automoto.util.ImageUtil; 
import com.automoto.util.PasswordUtil;
import com.automoto.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;

/**
 * Handles user registration process including form validation, duplicate checks,
 * profile picture upload, and database insertion. Supports multipart form data
 * for file uploads with size constraints.
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/register"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
    maxFileSize = 1024 * 1024 * 10,       // 10MB
    maxRequestSize = 1024 * 1024 * 50     // 50MB
)
public class Register extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final RegisterService registerService = new RegisterService();
    private final ImageUtil imageUtil = new ImageUtil();

    /**
     * Displays the registration form.
     * Forwards to register.jsp view page.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/pages/register.jsp").forward(request, response);
    }

    /**
     * Processes registration form submission.
     * Validates all fields, checks for duplicates, uploads profile image,
     * and creates new user account. Redirects to login page on success.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        boolean isValid = validateRegistrationForm(request, response);
        if (isValid) {
            response.sendRedirect("login?registered=true");
        } else {
            request.getRequestDispatcher("WEB-INF/pages/register.jsp").forward(request, response);
        }
    }

    /**
     * Validates all registration form fields and processes the registration.
     * Performs field validation, duplicate checks, image upload, and user creation.
     * 
     * @param request The HttpServletRequest containing form data
     * @param response The HttpServletResponse for redirects
     * @return true if registration was successful, false otherwise
     */
    private boolean validateRegistrationForm(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // Retrieve all form parameters
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String citizenshipNo = request.getParameter("citizenshipNo");
        String licenseNumber = request.getParameter("licenseNumber");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        Part profileImage = request.getPart("profileImage");

        boolean isValid = true;

        // --- Field Validations ---
        // First Name validation
        if (ValidationUtil.isNullOrEmpty(firstName)) {
            request.setAttribute("firstNameError", "Field shouldn't be empty.");
            isValid = false;
        } else if (!ValidationUtil.isAlphabetic(firstName)) {
            request.setAttribute("firstNameError", "Only letters are allowed.");
            isValid = false;
        }

        // Last Name validation
        if (ValidationUtil.isNullOrEmpty(lastName)) {
            request.setAttribute("lastNameError", "Field shouldn't be empty.");
            isValid = false;
        } else if (!ValidationUtil.isAlphabetic(lastName)) {
            request.setAttribute("lastNameError", "Only letters are allowed.");
            isValid = false;
        }

        // Email validation
        if (ValidationUtil.isNullOrEmpty(email)) {
            request.setAttribute("emailError", "Field shouldn't be empty.");
            isValid = false;
        } else if (!ValidationUtil.isValidEmail(email)) {
            request.setAttribute("emailError", "Invalid email format.");
            isValid = false;
        }

        // Phone validation
        if (ValidationUtil.isNullOrEmpty(phoneNumber)) {
            request.setAttribute("phoneError", "Field shouldn't be empty.");
            isValid = false;
        } else if (!ValidationUtil.isValidPhoneNumber(phoneNumber)) {
            request.setAttribute("phoneError", "Must start with 98 & contain 10 digits.");
            isValid = false;
        }

        // Citizenship validation
        if (ValidationUtil.isNullOrEmpty(citizenshipNo)) {
            request.setAttribute("citizenshipError", "Field shouldn't be empty.");
            isValid = false;
        } else if (!ValidationUtil.isValidDocument(citizenshipNo)) {
            request.setAttribute("citizenshipError", "Must be 10 digits.");
            isValid = false;
        }

        // License validation
        if (ValidationUtil.isNullOrEmpty(licenseNumber)) {
            request.setAttribute("licenseError", "Field shouldn't be empty.");
            isValid = false;
        } else if (!ValidationUtil.isValidDocument(licenseNumber)) {
            request.setAttribute("licenseError", "Must be 10 digits.");
            isValid = false;
        }

        // Password validation
        if (ValidationUtil.isNullOrEmpty(password)) {
            request.setAttribute("passwordError", "Field shouldn't be empty.");
            isValid = false;
        } else if (!ValidationUtil.isValidPassword(password)) {
            request.setAttribute("passwordError", "Min 8 chars, 1 uppercase, 1 number, 1 special.");
            isValid = false;
        }

        // Confirm Password validation
        if (ValidationUtil.isNullOrEmpty(confirmPassword)) {
            request.setAttribute("confirmPasswordError", "Field shouldn't be empty.");
            isValid = false;
        } else if (!ValidationUtil.doPasswordsMatch(password, confirmPassword)) {
            request.setAttribute("confirmPasswordError", "Passwords don't match.");
            isValid = false;
        }

        // Profile Image validation and upload
        String fileName = null;
        if (profileImage == null || profileImage.getSize() == 0) {
            request.setAttribute("profileImageError", "Please upload an image.");
            isValid = false;
        } else if (!ValidationUtil.isValidImageExtension(profileImage)) {
            request.setAttribute("profileImageError", "Only JPG, PNG, GIF formats allowed.");
            isValid = false;
        } else {
            fileName = imageUtil.getImageNameFromPart(profileImage);
            boolean uploadSuccess = imageUtil.uploadImage(profileImage, 
                getServletContext().getRealPath(""), "profile_images");
            
            if (!uploadSuccess) {
                request.setAttribute("profileImageError", "Failed to upload image. Please try again.");
                isValid = false;
            }
        }

        if (!isValid) {
            return false;
        }

        // --- Duplicate Checks ---
        if (registerService.isFieldValueExists("Email", email)) {
            request.setAttribute("emailError", "This email is already registered.");
            isValid = false;
        }
        
        if (registerService.isFieldValueExists("Phone_Number", phoneNumber)) {
            request.setAttribute("phoneError", "This phone number is already registered.");
            isValid = false;
        }
        
        if (registerService.isFieldValueExists("Citizenship_No", citizenshipNo)) {
            request.setAttribute("citizenshipError", "This citizenship number is already registered.");
            isValid = false;
        }
        
        if (registerService.isFieldValueExists("License_No", licenseNumber)) {
            request.setAttribute("licenseError", "This license number is already registered.");
            isValid = false;
        }
        
        if (!isValid) {
            return false;
        }

        // Password encryption
        String encryptedPassword = PasswordUtil.encrypt(email, password);
        if (encryptedPassword == null) {
            request.setAttribute("formError", "Password encryption failed. Please try again.");
            return false;
        }

        // Create and save user
        UserModel user = new UserModel(
                firstName, lastName, phoneNumber, email, encryptedPassword,
                citizenshipNo, licenseNumber, fileName
        );

        boolean success = registerService.addUser(user);
        if (!success) {
            request.setAttribute("formError", "Something went wrong. Please try again.");
            return false;
        }

        return true;
    }
}