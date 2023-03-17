package ie.provider;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import ie.commodity.CommodityManager;
import ie.exeption.CustomException;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;


public class Provider {
    private int id;
    private String name;
    private String registryDate;
    private ArrayList<String> comoditiesList;
    private  float avrageRate;


    @JsonCreator
    private Provider(){
        this.comoditiesList = new ArrayList<>();

    }

    @JsonProperty(value = "id", required = true)
    private void setUsername(int id){
        this.id = id;
    }
    @JsonProperty(value = "avrageRate")
    private void setAvrageRate(float avrageRate){
        this.avrageRate = avrageRate;
    }

    @JsonProperty(value = "name", required = true)
    private void setName(String name){
        this.name = name;
    }

    @JsonProperty(value = "registryDate", required = true)
    private void setRegistryDate(String registryDate){
        this.registryDate = registryDate;
    }

    @JsonGetter(value = "id")
    public int getId(){return this.id;}

    @JsonGetter(value = "name")
    public String getName(){return this.name;}

    @JsonGetter(value = "registryDate")
    public String getRegistryDate(){return this.registryDate;}

    @JsonGetter(value = "avrageRate")
    public float getAvrageRate(){return this.avrageRate;}

    @JsonGetter("commoditiesList")
    public List<String> getCommoditiesList() {
        return comoditiesList;
    }

    public void calculateAvrageRate() throws CustomException {
        float sum=0;
        var commodities= CommodityManager.getInstance().getElementsById(comoditiesList);
        for (var commodity: commodities){
            sum+=commodity.getRate();
        }
        this.avrageRate=sum/commodities.size();
    }

    public void addToCommoditiesList(String id) throws CustomException {

        this.comoditiesList.add(id);
        calculateAvrageRate();
    }

}
