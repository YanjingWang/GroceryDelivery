package edu.gatech.cs6310;

import java.util.Arrays;

public class User {
    public enum Role {
        ADMIN(0),
        MANAGER(1),
        CUSTOMER(2),
        ANONYMOUS(3);

        private final int value;
        Role(int value) {
            this.value = value;
        }

        public  final int getValue() {
            return value;
        }

        public static final Role getByValue(int value) {
            return Arrays.stream(Role.values()).filter(enumRole -> enumRole.value == value).findFirst().orElse(ANONYMOUS);
        }
    }


    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Role role = Role.CUSTOMER;

    public User (String inputFirstName,String inputLastName, String inputPhoneNumber) {
        this.firstName = inputFirstName;
        this.lastName = inputLastName;
        this.phoneNumber= inputPhoneNumber;
    }

    public User (String inputFirstName,String inputLastName, String inputPhoneNumber, Role role) {
        this.firstName = inputFirstName;
        this.lastName = inputLastName;
        this.phoneNumber= inputPhoneNumber;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "name:" + this.firstName + "_" + this.lastName + ",phone:" + phoneNumber;
    }
}