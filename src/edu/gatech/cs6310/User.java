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

    @Override
    public String toString() {
        return "name:" + this.firstName + "_" + this.lastName + ",phone:" + phoneNumber;
    }
}