package edu.core.java.auction.vo;


import java.util.Date;

/**
 * Created by Maxim on 19.02.2017.
 */
public class LotValueObject extends ValueObject {
    public double currentPrice;
    public Date endDate;
    public Long productId;

    public LotValueObject() {
    }

    public LotValueObject(Long id, Date endDate, Long productId, double currentPrice){
        this.id = id;
        this.currentPrice = currentPrice;
        this.endDate = endDate;
        this.productId = productId;
    }

    @Override
    public String toString(){
        return "ID: " + id + "\nCurrent price: " + currentPrice +
                "\nEnd date: " + endDate + "\nProduct ID: " + productId;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public double getCurrentPrice() {

        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
}
