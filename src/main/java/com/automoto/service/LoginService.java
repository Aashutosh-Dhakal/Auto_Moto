package com.automoto.service;

import com.automoto.config.DbConfig;
import com.automoto.model.UserModel;
import com.automoto.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Service class for handling login operations. Connects to the database,
 * verifies user credentials, and returns login status.
 */
public class LoginService {

    private Connection dbConn;
    private boolean isConnectionError = false;

    /**
     * Constructor initializes the database connection. Sets the connection error
     * flag if the connection fails.
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
     * Returns whether there was a connection error during initialization
     */
    public boolean isConnectionError() {
        return isConnectionError;
    }

    /**
     * Validates the user credentials against the database records.
     *
     * @param userModel the UserModel object containing user credentials
     * @return true if the user credentials are valid, false otherwise; null if a
     *         connection error occurs
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
     * 
     * @param email the user's email
     * @return the user's role or "customer" if not found/specified
     */
    public String getUserRole(String email) {
        if (isConnectionError) {
            return "customer"; // Default to customer if there's a connection error
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
     * Validates the password retrieved from the database.
     *
     * @param result    the ResultSet containing the email and password from
     *                  the database
     * @param userModel the UserModel object containing user credentials
     * @return true if the passwords match, false otherwise
     * @throws SQLException if a database access error occurs
     */
    private boolean validatePassword(ResultSet result, UserModel userModel) throws SQLException {
        String dbEmail = result.getString("Email");
        String dbPassword = result.getString("Password");

        return dbEmail.equals(userModel.getEmail())
                && PasswordUtil.decrypt(dbPassword, dbEmail).equals(userModel.getPassword());
    }
}