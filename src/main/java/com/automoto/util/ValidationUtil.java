package com.automoto.util;

import java.util.regex.Pattern;
import jakarta.servlet.http.Part;

/**
 * Utility class for validating various types of input data.
 * Provides static methods for common validation scenarios including:
 * - Null/empty checks
 * - Format validation (email, phone numbers, documents)
 * - Password strength validation
 * - Image file validation
 * - Password matching verification
 */
public class ValidationUtil {

    /**
     * Checks if a string is null or empty (after trimming whitespace).
     *
     * @param value the string to check
     * @return true if the string is null or empty, false otherwise
     */
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Validates if a string contains only alphabetic characters (a-z, A-Z).
     *
     * @param value the string to validate
     * @return true if the string contains only letters, false otherwise
     */
    public static boolean isAlphabetic(String value) {
        return value != null && value.matches("^[a-zA-Z]+$");
    }

    /**
     * Validates if a string starts with a letter and contains only alphanumeric characters.
     *
     * @param value the string to validate
     * @return true if the string starts with a letter and contains only letters/numbers
     */
    public static boolean isAlphanumericStartingWithLetter(String value) {
        return value != null && value.matches("^[a-zA-Z][a-zA-Z0-9]*$");
    }

    /**
     * Validates if a string is a properly formatted email address.
     *
     * @param email the email address to validate
     * @return true if the email format is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email != null && Pattern.matches(emailRegex, email);
    }

    /**
     * Validates if a string is a 10-digit phone number starting with '98'.
     *
     * @param number the phone number to validate
     * @return true if the phone number format is valid, false otherwise
     */
    public static boolean isValidPhoneNumber(String number) {
        return number != null && number.matches("^98\\d{8}$");
    }
    
    /**
     * Validates if a string is a 10-digit number.
     *
     * @param number the document number to validate
     * @return true if the document number format is valid, false otherwise
     */
    public static boolean isValidDocument(String number) {
        return number != null && number.matches("^\\d{10}$");
    }

    /**
     * Validates password strength requirements:
     * - At least 8 characters long
     * - Contains at least one uppercase letter
     * - Contains at least one digit
     * - Contains at least one special character (@$!%*?&)
     *
     * @param password the password to validate
     * @return true if the password meets strength requirements, false otherwise
     */
    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password != null && password.matches(passwordRegex);
    }

    /**
     * Validates if a file part has an allowed image extension (jpg, jpeg, png, gif).
     *
     * @param imagePart the file part to validate
     * @return true if the file has a valid image extension, false otherwise
     */
    public static boolean isValidImageExtension(Part imagePart) {
        if (imagePart == null || isNullOrEmpty(imagePart.getSubmittedFileName())) {
            return false;
        }
        String fileName = imagePart.getSubmittedFileName().toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif");
    }

    /**
     * Validates if two password strings match.
     *
     * @param password the original password
     * @param retypePassword the password to compare against
     * @return true if passwords match (non-null and equal), false otherwise
     */
    public static boolean doPasswordsMatch(String password, String retypePassword) {
        return password != null && password.equals(retypePassword);
    }
}