package Baloot.model;

import Baloot.model.DTO.CommentDTO;

import java.util.ArrayList;

public class Commodity {
    private int id;
    private String name;
    private Provider provider;
    private float price;
    private ArrayList<String> categories;
    private float rate;
    private int inStock;
    private String image;
    private ArrayList<Comment> comments;

    public Commodity(int id, String name, Provider provider,float price, ArrayList<String>categories, float rate, int inStock, String image){
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

    public int getId(){return id;}
    public float getPrice() {return price;}

    public void addComment(Comment comment){
        comments.add(comment);
    }


}
