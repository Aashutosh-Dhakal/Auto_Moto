package com.automoto.model;

public class UserModel {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private String citizenshipNo;
    private String licenseNo;
    private String profileImage;

    public UserModel() {}

    public UserModel(String firstName, String lastName, String phoneNumber, String email, 
            String password, String citizenshipNo, String licenseNo, String profileImage) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.password = password;
		this.citizenshipNo = citizenshipNo;
		this.licenseNo = licenseNo;
		this.profileImage = profileImage;
	}


    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCitizenshipNo() {
        return citizenshipNo;
    }

    public void setCitizenshipNo(String citizenshipNo) {
        this.citizenshipNo = citizenshipNo;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
