package Baloot.model.DTO;

import java.util.ArrayList;

public class ProviderDTO {
    private int id;
    private String name;
    private String registryDate;
    private ArrayList<String> commoditiesList;
    private  float averageRate;

    public void setId(int id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setRegistryDate(String registryDate){
        this.registryDate = registryDate;
    }
    public void setCommoditiesList(ArrayList<String>commoditiesList){
        this.commoditiesList = commoditiesList;
    }
    public void setAverageRate(float averageRate){
        this.averageRate = averageRate;
    }
    public int getId(){
        return this.id;
    }
    public String getName() {
        return name;
    }
    public String getRegistryDate(){
        return registryDate;
    }
    public ArrayList<String> getCommoditiesList() {
        return commoditiesList;
    }
    public float getAverageRate() {
        return averageRate;
    }
}
