package Baloot.model;

import Baloot.model.DTO.CommentDTO;
import Baloot.model.DTO.CommodityDTO;

import java.util.ArrayList;
import java.util.HashMap;

public class Commodity {


    private String id;
    private String name;
    private Provider provider;
    private float price;
    private ArrayList<String> categories;
    private float rate;
    private int inStock;
    private String image;
    private ArrayList<Comment> comments;
    private HashMap<String, Integer> userRateMap;

    public Commodity(String id, String name, Provider provider,float price, ArrayList<String>categories, float rate, int inStock, String image){
        this.id = id;
        this.name = name;
        this.provider = provider;
        this.price = price;
        this.categories = categories;
        this.rate = rate;
        this.inStock = inStock;
        this.image = image;
        this.comments = new ArrayList<>();
    }

    public String getId(){return id;}
    public float getPrice() {return price;}
    public Provider getProvider(){return this.provider;}

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public Integer getUserRate(String userId) {
        if(!userRateMap.containsKey(userId)) {
            return null;
        }
        return userRateMap.get(userId);
    }

    public CommodityDTO getDTO(){
        var DTO = new CommodityDTO();
        DTO.setId(Integer.parseInt(id));
        DTO.setName(name);
        DTO.setProvideName(provider.getName());
        DTO.setPrice(price);
        DTO.setCategories(categories);
        DTO.setRate(rate);
        DTO.setInStock(inStock);
        DTO.setImgUrl(image);

        return DTO;
    }


}
