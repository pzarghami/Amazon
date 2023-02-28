package ie.provider;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;


public class Provider {
    private int id;
    private String name;
    private String registryDate;
    private final HashMap<Integer, Float> commodityRateMap;

    @JsonCreator
    private Provider(){this.commodityRateMap=new HashMap<>();}

    @JsonProperty(value = "id", required = true)
    private void setUsername(int id){
        this.id = id;
    }

    @JsonProperty(value = "name", required = true)
    private void setBirthDate(String name){
        this.name = name;
    }

    @JsonProperty(value = "registryDate", required = true)
    private void setAddress(String registryDate){
        this.registryDate = registryDate;
    }

    public void setAverageRate(int commodityId,float rate){
       this.commodityRateMap.put(commodityId,rate);
    }

}
