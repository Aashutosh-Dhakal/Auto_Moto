package com.automoto.model;

/**
 * Represents a user entity in the AutoMoto system.
 * Contains all properties and behaviors related to user accounts.
 */
public class UserModel {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private String citizenshipNo;
    private String licenseNo;
    private String profileImage;

    /**
     * Default constructor for UserModel.
     * Creates an empty user instance.
     */
    public UserModel() {}

    /**
     * Parameterized constructor for UserModel.
     * Creates a user instance with all properties initialized.
     * 
     * @param firstName The user's first name
     * @param lastName The user's last name
     * @param phoneNumber The user's phone number
     * @param email The user's email address
     * @param password The user's account password
     * @param citizenshipNo The user's citizenship number
     * @param licenseNo The user's driving license number
     * @param profileImage The filename/path of the user's profile image
     */
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

    /**
     * Gets the user's first name.
     * @return The first name as a String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the user's first name.
     * @param firstName The first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the user's last name.
     * @return The last name as a String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the user's last name.
     * @param lastName The last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the user's phone number.
     * @return The phone number as a String
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the user's phone number.
     * @param phoneNumber The phone number to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the user's email address.
     * @return The email address as a String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     * @param email The email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's account password.
     * @return The password as a String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's account password.
     * @param password The password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's citizenship number.
     * @return The citizenship number as a String
     */
    public String getCitizenshipNo() {
        return citizenshipNo;
    }

    /**
     * Sets the user's citizenship number.
     * @param citizenshipNo The citizenship number to set
     */
    public void setCitizenshipNo(String citizenshipNo) {
        this.citizenshipNo = citizenshipNo;
    }

    /**
     * Gets the user's driving license number.
     * @return The license number as a String
     */
    public String getLicenseNo() {
        return licenseNo;
    }

    /**
     * Sets the user's driving license number.
     * @param licenseNo The license number to set
     */
    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    /**
     * Gets the user's profile image filename/path.
     * @return The image reference as a String
     */
    public String getProfileImage() {
        return profileImage;
    }

    /**
     * Sets the user's profile image filename/path.
     * @param profileImage The image reference to set
     */
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}