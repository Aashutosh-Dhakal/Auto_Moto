package com.automoto.service;

import com.automoto.config.DbConfig;
import com.automoto.model.UserModel;
import com.automoto.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileService {
    private Connection dbConn;

    public ProfileService() {
        try {
            this.dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("ProfileService: Database connection error: " + e.getMessage());
        }
    }

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