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

@WebServlet(asyncSupported = true, urlPatterns = {"/register"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class Register extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final RegisterService registerService = new RegisterService();
    private final ImageUtil imageUtil = new ImageUtil();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/pages/register.jsp").forward(request, response);
    }

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

    private boolean validateRegistrationForm(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

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
        if (ValidationUtil.isNullOrEmpty(firstName)) {
            request.setAttribute("firstNameError", "Field shouldn't be empty.");
            isValid = false;
        } else if (!ValidationUtil.isAlphabetic(firstName)) {
            request.setAttribute("firstNameError", "Only letters are allowed.");
            isValid = false;
        }

        if (ValidationUtil.isNullOrEmpty(lastName)) {
            request.setAttribute("lastNameError", "Field shouldn't be empty.");
            isValid = false;
        } else if (!ValidationUtil.isAlphabetic(lastName)) {
            request.setAttribute("lastNameError", "Only letters are allowed.");
            isValid = false;
        }

        if (ValidationUtil.isNullOrEmpty(email)) {
            request.setAttribute("emailError", "Field shouldn't be empty.");
            isValid = false;
        } else if (!ValidationUtil.isValidEmail(email)) {
            request.setAttribute("emailError", "Invalid email format.");
            isValid = false;
        }

        if (ValidationUtil.isNullOrEmpty(phoneNumber)) {
            request.setAttribute("phoneError", "Field shouldn't be empty.");
            isValid = false;
        } else if (!ValidationUtil.isValidPhoneNumber(phoneNumber)) {
            request.setAttribute("phoneError", "Must start with 98 & contain 10 digits.");
            isValid = false;
        }

        if (ValidationUtil.isNullOrEmpty(citizenshipNo)) {
            request.setAttribute("citizenshipError", "Field shouldn't be empty.");
            isValid = false;
        } else if (!ValidationUtil.isValidDocument(citizenshipNo)) {
            request.setAttribute("citizenshipError", "Must be 10 digits.");
            isValid = false;
        }

        if (ValidationUtil.isNullOrEmpty(licenseNumber)) {
            request.setAttribute("licenseError", "Field shouldn't be empty.");
            isValid = false;
        } else if (!ValidationUtil.isValidDocument(licenseNumber)) {
            request.setAttribute("licenseError", "Must be 10 digits.");
            isValid = false;
        }

        if (ValidationUtil.isNullOrEmpty(password)) {
            request.setAttribute("passwordError", "Field shouldn't be empty.");
            isValid = false;
        } else if (!ValidationUtil.isValidPassword(password)) {
            request.setAttribute("passwordError", "Min 8 chars, 1 uppercase, 1 number, 1 special.");
            isValid = false;
        }

        if (ValidationUtil.isNullOrEmpty(confirmPassword)) {
            request.setAttribute("confirmPasswordError", "Field shouldn't be empty.");
            isValid = false;
        } else if (!ValidationUtil.doPasswordsMatch(password, confirmPassword)) {
            request.setAttribute("confirmPasswordError", "Passwords don't match.");
            isValid = false;
        }

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


        String encryptedPassword = PasswordUtil.encrypt(email, password);
        if (encryptedPassword == null) {
            request.setAttribute("formError", "Password encryption failed. Please try again.");
            return false;
        }

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