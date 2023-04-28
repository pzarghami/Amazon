package Baloot.model.DTO;

import java.util.ArrayList;
import java.util.HashMap;

public class CommodityDTO {
    private int id;
    private String name;
    private int provideId;
    private float price;
    private ArrayList<String> categories;
    private float rate;
    private int inStock;
    private  String imgUrl;
    private HashMap<String, Float> commodityRateMap;
    private ArrayList<String> comments;

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setProvideId(int provideId) {
        this.provideId = provideId;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }
    public void setRate(float rate) {
        this.rate = rate;
    }
    public void setInStock(int inStock) {
        this.inStock = inStock;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public void setCommodityRateMap(HashMap<String, Float> commodityRateMap) {
        this.commodityRateMap = commodityRateMap;
    }
    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getProvideId() {
        return provideId;
    }
    public float getPrice() {
        return price;
    }
    public ArrayList<String> getCategories() {
        return categories;
    }
    public float getRate() {
        return rate;
    }
    public int getInStock() {
        return inStock;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public HashMap<String, Float> getCommodityRateMap() {
        return commodityRateMap;
    }
    public ArrayList<String> getComments() {
        return comments;
    }
}
