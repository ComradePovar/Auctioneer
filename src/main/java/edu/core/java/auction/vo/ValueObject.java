package edu.core.java.auction.vo;

/**
 * Created by Maxim on 19.02.2017.
 */
public abstract class ValueObject {
    public Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
