package Baloot.model.DTO;

import java.util.ArrayList;

public class ProviderDTO {
    private int id;
    private String name;
    private String registryDate;
    private ArrayList<CommodityBriefDTO> commoditiesList;
    private String image;

    public int getId() {
        return this.id;
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

    public String getRegistryDate() {
        return registryDate;
    }

    public void setRegistryDate(String registryDate) {
        this.registryDate = registryDate;
    }

    public ArrayList<CommodityBriefDTO> getCommoditiesList() {
        return commoditiesList;
    }

    public void setCommoditiesList(ArrayList<CommodityBriefDTO> commoditiesList) {
        this.commoditiesList = commoditiesList;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
