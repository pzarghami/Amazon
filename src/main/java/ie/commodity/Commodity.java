package ie.commodity;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ie.CustomException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Commodity {
    private int id;
    private String name;
    private int provideId;
    private float price;
    private ArrayList<String> categories;
    private float rate;
    private int inStock;
    private final HashMap<String, Integer> commodityRateMap;


    @JsonCreator
    private Commodity(){
        this.categories= new ArrayList<>();
        this.commodityRateMap = new HashMap<>();
    }

    @JsonProperty(value = "id", required = true)
    private void setId(int id){
        this.id = id;
    }

    @JsonProperty(value = "name", required = true)
    private void setName(String name){
        this.name = name;
    }

    @JsonProperty(value = "providerId", required = true)
    private void setProvideId(int id){this.provideId = id;}

    @JsonProperty(value = "price", required = true)
    private void setPrice(float price){
        this.price = price;
    }

    @JsonProperty(value = "categories", required = true)
    private void setCategories(ArrayList<String> cat){
        this.categories = cat;
    }

    @JsonProperty(value = "rating", required = true)
    private void setRate(float rate){ this.rate = rate; }

    @JsonProperty(value = "inStock", required = true)
    private void setInStock(int inStock){
        this.inStock = inStock;
    }

    @JsonGetter(value = "id")
    private int getId() {return this.id;}

    @JsonGetter(value = "name")
    private String getName() {return this.name;}

    @JsonGetter(value = "providerId")
    private int getProvideId() {return this.provideId;}

    @JsonGetter(value = "price")
    private float getPrice() {return this.price;}

    @JsonGetter(value = "categories")
    private ArrayList<String> getCategories() {return this.categories;}

    @JsonGetter(value = "rating")
    private float getRate() {return this.rate;}

    @JsonGetter(value = "inStock")
    private int getInStock() {return this.inStock;}

    public void buy()throws CustomException{
        if(this.inStock<=0)
            throw new CustomException("there is no enough commodity.");
        this.inStock -= 1;
    }
    public float addRate(String username,int rate){
        this.commodityRateMap.put(username,rate);
        int sum = 0;
        for (Map.Entry<String,Integer>map : this.commodityRateMap.entrySet()){
            sum += map.getValue();
        }
        this.rate = sum / this.commodityRateMap.size();
        return this.rate;
    }
}
