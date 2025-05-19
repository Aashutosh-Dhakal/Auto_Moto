package com.automoto.service;

import com.automoto.config.DbConfig;
import com.automoto.model.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Service class for handling user registration operations in the AutoMoto system.
 * Manages user registration and field validation.
 */
public class RegisterService {

    /**
     * Checks if a field value already exists in the database.
     * @param fieldName The name of the database field to check
     * @param value The value to check for existence
     * @return true if the value exists, false otherwise or if an error occurs
     */
    public boolean isFieldValueExists(String fieldName, String value) {
        String sql = "SELECT COUNT(*) FROM User WHERE " + fieldName + " = ?";
        
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Adds a new user to the database.
     * @param user The UserModel object containing user details
     * @return true if the user was successfully added, false otherwise
     */
    public boolean addUser(UserModel user) {
        boolean isInserted = false;

        String sql = "INSERT INTO User (First_Name, Last_Name, Phone_Number, "
                   + "Email, Password, Citizenship_No, License_No, Image) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPhoneNumber());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getCitizenshipNo());
            ps.setString(7, user.getLicenseNo());
            ps.setString(8, user.getProfileImage());

            isInserted = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isInserted;
    }
}