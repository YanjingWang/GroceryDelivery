package edu.gatech.cs6310;

public class Pilot {

    private String accountId;
    private User user;
    private String taxId;
    private String licenseId;
    private Integer experience;

    private Drone assignedDrone;

    public Pilot(String accountId, User user, String taxId, String licenseId, Integer experience) {
        this.accountId = accountId;
        this.user = user;
        this.taxId = taxId;
        this.licenseId = licenseId;
        this.experience = experience;
    }

    public void assignDrone(Drone drone) {
        this.assignedDrone = drone;
    }

    public Drone getAssignedDrone() {
       return this.assignedDrone;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public String getName() {
        return this.user.getName();
    }

    public void incrementExperience() {
        this.experience++;
    }

    @Override
    public String toString() {
        return this.user + ",taxID:" + this.taxId + ",licenseID:" + this.licenseId + ",experience:" + this.experience;
    }
}

