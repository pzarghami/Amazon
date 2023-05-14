package Baloot.model;

import java.util.ArrayList;

public class Provider {
    private int id;
    private String name;
    private String registryDate;
    private String image;
    private ArrayList<Commodity> commoditiesList;
    private float averageRate;

    public Provider(int id, String name, String registryDate, String image) {
        this.id = id;
        this.name = name;
        this.registryDate = registryDate;
        this.image = image;
        this.commoditiesList = new ArrayList<>();
    }

    public int getId(){return this.id;}
    public String getName(){return this.name;}

    public void addCommodity(Commodity commodity) {
        commoditiesList.add(commodity);
    }
}
