package com.automoto.service;

import com.automoto.config.DbConfig;
import com.automoto.model.BikeModel;
import com.automoto.model.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides administrative services for managing bikes and users in the AutoMoto system.
 * Handles database operations related to bike and user management.
 */
public class AdminService {
    private Connection dbConn;
    private boolean isConnectionError = false;

    /**
     * Default constructor for AdminService.
     * Initializes database connection.
     */
    public AdminService() {
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
     * Retrieves all bikes from the database.
     * @return List of BikeModel objects containing all bikes
     */
    public List<BikeModel> getAllBikes() {
        List<BikeModel> bikes = new ArrayList<>();
        if (isConnectionError) {
            return bikes;
        }

        String query = "SELECT * FROM Bike";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                BikeModel bike = new BikeModel();
                bike.setPlateNo(result.getString("Plate_No"));
                bike.setBrand(result.getString("Brand"));
                bike.setModel(result.getString("Model"));
                bike.setType(result.getString("Type"));
                bike.setBikeCondition(result.getString("Bike_Condition"));
                bike.setImage(result.getString("Image"));
                bikes.add(bike);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bikes;
    }

    /**
     * Retrieves all customer users from the database.
     * @return List of UserModel objects containing all customer users
     */
    public List<UserModel> getAllUsers() {
        List<UserModel> users = new ArrayList<>();
        if (isConnectionError) {
            return users;
        }

        String query = "SELECT * FROM User WHERE Role = 'customer'";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                UserModel user = new UserModel();
                user.setFirstName(result.getString("First_Name"));
                user.setLastName(result.getString("Last_Name"));
                user.setEmail(result.getString("Email"));
                user.setPhoneNumber(result.getString("Phone_Number"));
                user.setCitizenshipNo(result.getString("Citizenship_No"));
                user.setLicenseNo(result.getString("License_No"));
                user.setProfileImage(result.getString("Image"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Adds a new bike to the database.
     * @param bike The BikeModel object containing bike details
     * @return true if bike was added successfully, false otherwise
     */
    public boolean addBike(BikeModel bike) {
        String query = "INSERT INTO Bike (Plate_No, Brand, Model, Type, Bike_Condition, Image) " +
                      "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, bike.getPlateNo());
            stmt.setString(2, bike.getBrand());
            stmt.setString(3, bike.getModel());
            stmt.setString(4, bike.getType());
            stmt.setString(5, bike.getBikeCondition());
            stmt.setString(6, bike.getImage());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Updates an existing bike in the database.
     * @param bike The BikeModel object containing updated bike details
     * @return true if bike was updated successfully, false otherwise
     */
    public boolean updateBike(BikeModel bike) {
        String query = "UPDATE Bike SET Brand = ?, Model = ?, Type = ?, " +
                      "Bike_Condition = ?, Image = ? WHERE Plate_No = ?";
        
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, bike.getBrand());
            stmt.setString(2, bike.getModel());
            stmt.setString(3, bike.getType());
            stmt.setString(4, bike.getBikeCondition());
            stmt.setString(5, bike.getImage());
            stmt.setString(6, bike.getPlateNo());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Deletes a bike from the database.
     * @param plateNo The plate number of the bike to delete
     * @return true if bike was deleted successfully, false otherwise
     */
    public boolean deleteBike(String plateNo) {
        String query = "DELETE FROM Bike WHERE Plate_No = ?";
        
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, plateNo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Deletes a user from the database.
     * @param email The email of the user to delete
     * @return true if user was deleted successfully, false otherwise
     */
    public boolean deleteUser(String email) {
        String query = "DELETE FROM User WHERE Email = ?";
        
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Closes the database connection.
     */
    public void closeConnection() {
        if (dbConn != null) {
            try {
                dbConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}