package edu.core.java.auction.vo;

/**
 * Created by Maxim on 19.02.2017.
 */
public class BidValueObject extends ValueObject {
    public Long buyerId;
    public Long lotId;
    public double amount;

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public BidValueObject() {
    }

    public BidValueObject(Long id, Long lotId, Long buyerId, double amount){
        this.id = id;
        this.lotId = lotId;
        this.buyerId = buyerId;
        this.amount = amount;
    }

    @Override
    public String toString(){
        return "ID: " + id + "\nBuyer ID: " + buyerId + "\nAmount: " + amount;
    }
}
