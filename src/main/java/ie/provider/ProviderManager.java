package ie.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.Baloot;
import ie.Constant;

import java.util.HashMap;

public class ProviderManager {
    private final HashMap<Integer, Provider> providerMap;
    private final Baloot database;
    private final ObjectMapper mapper;


    public ProviderManager (Baloot database) {
        mapper = new ObjectMapper();
        this.database = database;
        providerMap = new HashMap<>();

    }

    public String  updateOrAddProvider(String jsonData) throws  JsonProcessingException {
        int id = mapper.readTree(jsonData).get("id").asInt();
        if(isIDValid(id)){
            updateUser(id,jsonData);
        }else{
            addUser(id,jsonData);
        }
        return Constant.PROVIDER_ADD;
    }

    public void setAverageRate(int providerId,float rate,int commodityId){
        var provider=providerMap.get(providerId);
        provider.setAverageRate(commodityId,rate);
    }
    private void updateUser(int id, String jsonData) throws JsonProcessingException{
        mapper.readerForUpdating(providerMap.get(id)).readValue(jsonData);
    }

    private void addUser(int id,String jsonData) throws JsonProcessingException{
        var newProvider = mapper.readValue(jsonData, Provider.class);
        providerMap.put(id,newProvider);
    }

    public boolean isIDValid(int id){
        return providerMap.containsKey(id);
    }

}
