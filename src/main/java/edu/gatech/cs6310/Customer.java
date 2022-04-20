package edu.gatech.cs6310;

public class Customer {
    private String customerId;
    private User user;
    private Integer rating;
    private Long credits;
    private Long usedCredits = 0L;

    public Customer(String customerId, User user, Integer rating, Long credits) {
        this.customerId = customerId;
        this.user = user;
        this.rating = rating;
        this.credits = credits;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getRating(){ return rating;}

    public Long getCredits(){ return credits;}

    public String getAccountId() {
        return this.customerId;
    }

    public void updateUsedCredits(Long credits) {
        usedCredits += credits;
    }

    public boolean canBuy(Long totalPrice) {
        return getRemainingCredits().compareTo(usedCredits + totalPrice) >= 0;
    }

    public Long getRemainingCredits() {
        return credits - usedCredits;
    }

    @Override
    public String toString() {
        return user + ",rating:" + rating + ",credit:" + getRemainingCredits();
    }
}
