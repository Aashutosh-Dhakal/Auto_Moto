package com.automoto.model;

public class BikeModel {
    private String plateNo;
    private String brand;
    private String model;
    private String type;
    private String bikeCondition;
    private String image;
    
    public BikeModel() {}
    
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

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBikeCondition() {
        return bikeCondition;
    }

    public void setBikeCondition(String bikeCondition) {
        this.bikeCondition = bikeCondition;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}