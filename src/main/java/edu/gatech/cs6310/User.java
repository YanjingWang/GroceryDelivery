package edu.gatech.cs6310;

public class User {
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public User (String inputFirstName,String inputLastName, String inputPhoneNumber) {
        this.firstName = inputFirstName;
        this.lastName = inputLastName;
        this.phoneNumber= inputPhoneNumber;
    }

    public String getName() {
        return this.firstName + "_" + this.lastName;
    }

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

    @Override
    public String toString() {
        return "name:" + this.firstName + "_" + this.lastName + ",phone:" + phoneNumber;
    }
}