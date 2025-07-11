package com.automoto.service;

import com.automoto.config.DbConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Service class for handling bike rental operations in the AutoMoto system.
 * Manages the process of renting bikes and maintaining rental records.
 */
public class RentalService {
    private Connection dbConn;

    /**
     * Default constructor for RentalService.
     * Initializes database connection.
     */
    public RentalService() {
        try {
            this.dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("RentalService: Database connection error: " + e.getMessage());
        }
    }

    /**
     * Rents a bike to a user.
     * @param userEmail The email of the user renting the bike
     * @param plateNo The plate number of the bike being rented
     * @return true if rental was successful, false otherwise
     */
    public boolean rentBike(String userEmail, String plateNo) {
        if (dbConn == null) {
            System.err.println("DB connection is null in RentalService.");
            return false;
        }

        try {
            // First get the bike_id from plate_no
            int bikeId = getBikeIdByPlateNo(plateNo);
            if (bikeId == -1) {
                System.err.println("Bike not found with plate number: " + plateNo);
                return false;
            }

            // Get user_id from email
            int userId = getUserIdByEmail(userEmail);
            if (userId == -1) {
                System.err.println("User not found with email: " + userEmail);
                return false;
            }

            // Insert into user_bike table
            String query = "INSERT INTO user_bike (User_ID, Bike_ID) VALUES (?, ?)";
            try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
                stmt.setInt(1, userId);
                stmt.setInt(2, bikeId);
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error renting bike: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Retrieves bike ID by plate number.
     * @param plateNo The plate number of the bike
     * @return bike ID if found, -1 if not found
     * @throws SQLException if database access error occurs
     */
    private int getBikeIdByPlateNo(String plateNo) throws SQLException {
        String query = "SELECT Bike_ID FROM bike WHERE Plate_No = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, plateNo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("Bike_ID");
            }
            return -1;
        }
    }
    
    /**
     * Retrieves user ID by email address.
     * @param email The email address of the user
     * @return user ID if found, -1 if not found
     * @throws SQLException if database access error occurs
     */
    public int getUserIdByEmail(String email) throws SQLException {
        String query = "SELECT User_ID FROM user WHERE Email = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("User_ID");
            }
            return -1;
        }
    }
}