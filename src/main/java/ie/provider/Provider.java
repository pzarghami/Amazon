package ie.provider;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;


public class Provider {
    private int id;
    private String name;
    private String registryDate;
    private final HashMap<Integer, Float> commodityRateMap;
    private ArrayList<String> comoditiesList;


    @JsonCreator
    private Provider(){
        this.commodityRateMap=new HashMap<>();
        this.comoditiesList = new ArrayList<>();
    }

    @JsonProperty(value = "id", required = true)
    private void setUsername(int id){
        this.id = id;
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


    @JsonGetter("commoditiesList")
    public List<String> getCommoditiesList() {
        return comoditiesList;
    }

    public void setAverageRate(int commodityId,float rate){
       this.commodityRateMap.put(commodityId,rate);
    }

    public void addToCommoditiesList(String id){
        this.comoditiesList.add(id);
    }

}
