package com.automoto.service;

import com.automoto.config.DbConfig;
import com.automoto.model.UserModel;
import com.automoto.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Service class for handling login operations in the AutoMoto system.
 * Handles user authentication and role retrieval from the database.
 */
public class LoginService {

    private Connection dbConn;
    private boolean isConnectionError = false;

    /**
     * Default constructor for LoginService.
     * Initializes database connection and sets error flag if connection fails.
     */
    public LoginService() {
        try {
            dbConn = DbConfig.getDbConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
            isConnectionError = true;
        }
    }

    /**
     * Checks if there was a connection error during initialization.
     * @return true if connection error occurred, false otherwise
     */
    public boolean isConnectionError() {
        return isConnectionError;
    }

    /**
     * Authenticates user credentials against the database.
     * @param userModel The UserModel object containing login credentials
     * @return true if authentication successful, false if failed, 
     *         null if connection error occurred
     */
    public Boolean loginUser(UserModel userModel) {
        if (isConnectionError) {
            System.out.println("Connection Error!");
            return null;
        }

        String query = "SELECT Email, Password FROM User WHERE Email = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, userModel.getEmail());
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                return validatePassword(result, userModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return false;
    }

    /**
     * Retrieves the user's role from the database.
     * @param email The email address of the user
     * @return The user's role as String, defaults to "customer" if not specified
     */
    public String getUserRole(String email) {
        if (isConnectionError) {
            return "customer"; 
        }

        String query = "SELECT Role FROM User WHERE Email = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                String role = result.getString("Role");
                return (role != null && !role.isEmpty()) ? role : "customer";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "customer";
    }

    /**
     * Validates the user's password against the encrypted database password.
     * @param result The ResultSet containing database credentials
     * @param userModel The UserModel object containing user credentials
     * @return true if passwords match, false otherwise
     * @throws SQLException if database access error occurs
     */
    private boolean validatePassword(ResultSet result, UserModel userModel) throws SQLException {
        String dbEmail = result.getString("Email");
        String dbPassword = result.getString("Password");

        return dbEmail.equals(userModel.getEmail())
                && PasswordUtil.decrypt(dbPassword, dbEmail).equals(userModel.getPassword());
    }
}