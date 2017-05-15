package edu.core.java.auction.vo;

/**
 * Created by Maxim on 19.02.2017.
 */
public class ProductValueObject extends ValueObject {
    public String title;
    public String description;
    public Long ownerId;

    public ProductValueObject() {
    }

    public ProductValueObject(Long id, String title, String description, Long ownerId){
        this.id = id;
        this.title = title;
        this.description = description;
        this.ownerId = ownerId;
    }

    @Override
    public String toString(){
        return "ID: " + id + "\nTitle: " + title + "\nDescription: " + description + "\nOwner ID: " + ownerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
