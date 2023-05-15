package Baloot.model.DTO;

import java.util.ArrayList;
import java.util.HashMap;

public class CommodityDTO {
    private int id;
    private String name;
    private String provideName;
    private float price;
    private ArrayList<String> categories;
    private double rate;
    private int inStock;
    private  String imgUrl;
    private ArrayList<String> comments;
    private Integer UserRate;

    public void setId(int id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setProvideName(String provideName) {this.provideName = provideName;}
    public void setPrice(float price) {this.price = price;}
    public void setCategories(ArrayList<String> categories) {this.categories = categories;}
    public void setRate(double rate) {this.rate = rate;}
    public void setInStock(int inStock) {this.inStock = inStock;}
    public void setImgUrl(String imgUrl) {this.imgUrl = imgUrl;}
    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }
    public void setUserRate(Integer userRate){this.UserRate = userRate;}

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getProvideName() {return provideName;}
    public float getPrice() {
        return price;
    }
    public ArrayList<String> getCategories() {
        return categories;
    }
    public double getRate() {
        return rate;
    }
    public int getInStock() {
        return inStock;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public ArrayList<String> getComments() {
        return comments;
    }
    public Integer getUserRate() {return UserRate; }
}
