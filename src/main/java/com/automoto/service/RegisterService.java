package com.automoto.service;

import com.automoto.config.DbConfig;
import com.automoto.model.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterService {

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