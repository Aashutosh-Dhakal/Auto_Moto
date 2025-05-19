package com.automoto.model;

/**
 * Represents a bike entity in the AutoMoto system.
 * Contains all properties and behaviors related to bikes available for rental.
 */
public class BikeModel {
    private String plateNo;
    private String brand;
    private String model;
    private String type;
    private String bikeCondition;
    private String image;
    
    /**
     * Default constructor for BikeModel.
     * Creates an empty bike instance.
     */
    public BikeModel() {}
    
    /**
     * Parameterized constructor for BikeModel.
     * Creates a bike instance with all properties initialized.
     * 
     * @param plateNo The license plate number of the bike
     * @param brand The manufacturer brand of the bike
     * @param model The specific model of the bike
     * @param type The category/type of the bike (e.g., sport, cruiser)
     * @param bikeCondition The current condition of the bike
     * @param image The filename/path of the bike's image
     */
    public BikeModel(String plateNo, String brand, String model, String type, String bikeCondition,
            String image) {
        super();
        this.plateNo = plateNo;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.bikeCondition = bikeCondition;
        this.image = image;
    }

    /**
     * Gets the bike's license plate number.
     * @return The plate number as a String
     */
    public String getPlateNo() {
        return plateNo;
    }

    /**
     * Sets the bike's license plate number.
     * @param plateNo The plate number to set
     */
    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    /**
     * Gets the bike's brand/manufacturer.
     * @return The brand name as a String
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the bike's brand/manufacturer.
     * @param brand The brand name to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Gets the bike's model name.
     * @return The model name as a String
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the bike's model name.
     * @param model The model name to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets the bike's type/category.
     * @return The bike type as a String
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the bike's type/category.
     * @param type The bike type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the bike's current condition.
     * @return The condition description as a String
     */
    public String getBikeCondition() {
        return bikeCondition;
    }

    /**
     * Sets the bike's current condition.
     * @param bikeCondition The condition description to set
     */
    public void setBikeCondition(String bikeCondition) {
        this.bikeCondition = bikeCondition;
    }

    /**
     * Gets the bike's image filename/path.
     * @return The image reference as a String
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the bike's image filename/path.
     * @param image The image reference to set
     */
    public void setImage(String image) {
        this.image = image;
    }
}