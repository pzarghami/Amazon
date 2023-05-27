package Baloot.model.DTO;

import java.util.ArrayList;
import java.util.HashMap;

public class CommodityDTO {
    private int id;
    private String name;
    private String provideName;
    private int providerId;
    private float price;
    private ArrayList<String> categories;
    private double rate;
    private int inStock;
    private String imgUrl;
    private ArrayList<CommentDTO> comments;
    private Integer UserRate;
    private ArrayList<CommodityBriefDTO> suggestionCommodity;
    private int numOfRate;
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvideName() {
        return provideName;
    }

    public void setProvideName(String provideName) {
        this.provideName = provideName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public ArrayList<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(ArrayList<CommentDTO> comments) {
        this.comments = comments;
    }

    public Integer getUserRate() {
        return UserRate;
    }

    public void setUserRate(Integer userRate) {
        this.UserRate = userRate;
    }

    public ArrayList<CommodityBriefDTO> getSuggestionCommodity() {
        return suggestionCommodity;
    }

    public void setSuggestionCommodity(ArrayList<CommodityBriefDTO> suggestionCommodity) {
        this.suggestionCommodity = suggestionCommodity;
    }

    public int getNumOfRate() {
        return numOfRate;
    }

    public void setNumOfRate(int numOfRate) {
        this.numOfRate = numOfRate;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
