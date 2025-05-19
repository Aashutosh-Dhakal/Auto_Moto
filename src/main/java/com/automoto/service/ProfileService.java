package com.automoto.service;

import com.automoto.config.DbConfig;
import com.automoto.model.UserModel;
import com.automoto.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Service class for handling user profile operations in the AutoMoto system.
 * Manages user details, profile pictures, and credential verification.
 */
public class ProfileService {
    private Connection dbConn;

    /**
     * Default constructor for ProfileService.
     * Initializes database connection.
     */
    public ProfileService() {
        try {
            this.dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("ProfileService: Database connection error: " + e.getMessage());
        }
    }

    /**
     * Retrieves user details from the database.
     * @param username The email address of the user
     * @return UserModel object containing user details, or null if not found
     */
    public UserModel getUserDetails(String username) {
        if (dbConn == null) {
            System.err.println("DB connection is null in ProfileService.");
            return null;
        }

        String query = "SELECT * FROM user WHERE email = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                UserModel user = new UserModel();
                user.setFirstName(rs.getString("First_Name"));
                user.setLastName(rs.getString("Last_Name"));
                user.setPhoneNumber(rs.getString("Phone_Number"));
                user.setEmail(rs.getString("Email"));
                user.setCitizenshipNo(rs.getString("Citizenship_No"));
                user.setLicenseNo(rs.getString("License_No"));
                user.setProfileImage(rs.getString("Image"));
                return user;
            }

        } catch (SQLException e) {
            System.err.println("Error fetching user details: " + e.getMessage());
        }

        return null;
    }

    /**
     * Updates user profile information in the database.
     * @param userModel The UserModel object containing updated details
     * @param username The original email address of the user
     * @return true if update was successful, false otherwise
     */
    public Boolean editUser(UserModel userModel, String username) {
        if (dbConn == null) {
            System.err.println("Database connection is not available.");
            return false;
        }
        String updateQuery = "UPDATE user SET First_Name = ?, Last_Name = ?, Phone_Number = ?, Email = ?, Citizenship_No = ?, License_No = ? WHERE Email = ?";

        try (PreparedStatement ps = dbConn.prepareStatement(updateQuery)) {
            ps.setString(1, userModel.getFirstName());
            ps.setString(2, userModel.getLastName());
            ps.setString(3, userModel.getPhoneNumber());
            ps.setString(4, userModel.getEmail());
            ps.setString(5, userModel.getCitizenshipNo());
            ps.setString(6, userModel.getLicenseNo());
            ps.setString(7, username);
            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates the user's profile picture in the database.
     * @param email The email address of the user
     * @param imageName The filename of the new profile picture
     * @return true if update was successful, false otherwise
     */
    public boolean updateProfilePicture(String email, String imageName) {
        if (dbConn == null) {
            System.err.println("Database connection is not available.");
            return false;
        }
        
        String updateQuery = "UPDATE user SET Image = ? WHERE Email = ?";
        try (PreparedStatement ps = dbConn.prepareStatement(updateQuery)) {
            ps.setString(1, imageName);
            ps.setString(2, email);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Error updating profile picture: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Verifies if the provided password matches the user's current password.
     * @param email The email address of the user
     * @param password The password to verify
     * @return true if password matches, false otherwise
     */
    public boolean verifyCurrentPassword(String email, String password) {
        if (dbConn == null) {
            return false;
        }
        
        String query = "SELECT Password FROM user WHERE Email = ?";
        try (PreparedStatement ps = dbConn.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                String encryptedPassword = rs.getString("Password");
                String decryptedPassword = PasswordUtil.decrypt(encryptedPassword, email);
                return password.equals(decryptedPassword);
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Updates the user's password in the database.
     * @param email The email address of the user
     * @param newPassword The new password to set
     * @return true if update was successful, false otherwise
     */
    public boolean updatePassword(String email, String newPassword) {
        if (dbConn == null) {
            return false;
        }
        
        String encryptedPassword = PasswordUtil.encrypt(email, newPassword);
        if (encryptedPassword == null) {
            return false;
        }
        
        String updateQuery = "UPDATE user SET Password = ? WHERE Email = ?";
        try (PreparedStatement ps = dbConn.prepareStatement(updateQuery)) {
            ps.setString(1, encryptedPassword);
            ps.setString(2, email);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if an email address is already taken by another user.
     * @param email The email address to check
     * @param username The current user's email address
     * @return true if email is taken by another user, false otherwise
     */
    public boolean isEmailTaken(String email, String username) {
        if (dbConn == null) {
            return false;
        }
        String query = "SELECT 1 FROM user WHERE Email = ? AND Email != ?";
        try (PreparedStatement ps = dbConn.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if a phone number is already taken by another user.
     * @param phone The phone number to check
     * @param username The current user's email address
     * @return true if phone number is taken by another user, false otherwise
     */
    public boolean isPhoneTaken(String phone, String username) {
        if (dbConn == null) {
            return false;
        }
        String query = "SELECT 1 FROM user WHERE Phone_Number = ? AND Email != ?";
        try (PreparedStatement ps = dbConn.prepareStatement(query)) {
            ps.setString(1, phone);
            ps.setString(2, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if a citizenship number is already taken by another user.
     * @param citizenshipNo The citizenship number to check
     * @param username The current user's email address
     * @return true if citizenship number is taken by another user, false otherwise
     */
    public boolean isCitizenshipNoTaken(String citizenshipNo, String username) {
        if (dbConn == null) {
            return false;
        }
        String query = "SELECT 1 FROM user WHERE Citizenship_No = ? AND Email != ?";
        try (PreparedStatement ps = dbConn.prepareStatement(query)) {
            ps.setString(1, citizenshipNo);
            ps.setString(2, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if a license number is already taken by another user.
     * @param licenseNo The license number to check
     * @param username The current user's email address
     * @return true if license number is taken by another user, false otherwise
     */
    public boolean isLicenseNoTaken(String licenseNo, String username) {
        if (dbConn == null) {
            return false;
        }
        String query = "SELECT 1 FROM user WHERE License_No = ? AND Email != ?";
        try (PreparedStatement ps = dbConn.prepareStatement(query)) {
            ps.setString(1, licenseNo);
            ps.setString(2, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}