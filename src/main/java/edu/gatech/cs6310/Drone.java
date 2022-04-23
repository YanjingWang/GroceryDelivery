package edu.gatech.cs6310;

public class Drone {

    private String id;
    private Long totalCapacity;
    private Long remainingCapacity;
    private Integer tripsCompleted;
    private Integer maximumDeliveries;
    private String storeName = null;

    // Refers to the accountId of the pilot that is currently controlling the drone
    private Pilot pilot = null;
    private static Controller controller = new Controller();

    Drone(String id, Long totalCapacity, Integer maximumDeliveries) {
        this.id = id;
        this.totalCapacity = totalCapacity;
        this.remainingCapacity = totalCapacity;
        this.tripsCompleted = 0;
        this.maximumDeliveries = maximumDeliveries;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setRemainingCapacity(Long remainingCapacity) {
        this.remainingCapacity = remainingCapacity;
    }

    public void setTripsCompleted(Integer tripsCompleted) {
        this.tripsCompleted = tripsCompleted;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    public Long getTotalCapacity() {
        return totalCapacity;
    }

    public Long getRemainingCapacity() {
        return remainingCapacity;
    }

    public void addPayload(Long weight) {
        remainingCapacity -= weight;
    }

    public void incrementTripsCompleted() {
        tripsCompleted++;
    }

    public void assignPilot(Pilot pilot) {
        this.pilot = pilot;
        this.pilot.assignDrone(this);
    }

    public Pilot getAssignedPilot() {
        return this.pilot;
    }

    public boolean canCarry(Long weight) {
        return (this.remainingCapacity - weight) > 0;
    }

    public void unassignPilot() {
        this.pilot = null;
    }

    public String getId() {
        return this.id;
    }

    public Integer tripsLeft() {
        return maximumDeliveries - tripsCompleted;
    }

    public Integer getTripsCompleted() {
        return tripsCompleted;
    }
    public Integer getMaximumDeliveries() {
        return maximumDeliveries;
    }
    public Pilot getPilot() {
        return pilot;
    }




    @Override
    public String toString() {
        return ("droneID:" + this.id + ",total_cap:" + this.totalCapacity + ",num_orders:" + this.tripsCompleted
                    + ",remaining_cap:" + this.remainingCapacity + ",trips_left:" + this.tripsLeft()
                + ( pilot == null ? "" : ",flown_by:" + pilot.getName())  );
    }
}

