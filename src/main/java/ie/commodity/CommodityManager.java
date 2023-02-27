package ie.commodity;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.Baloot;
import ie.CustomException;


import java.util.HashMap;

public class CommodityManager {

    private final HashMap<Integer, Commodity> commodityHashMap;
    private final Baloot database;
    private final ObjectMapper mapper;

    public CommodityManager (Baloot database) {
        mapper = new ObjectMapper();
        this.database = database;
        commodityHashMap = new HashMap<>();
    }

    public String addCommodity(String jsonData) throws JsonProcessingException , CustomException{
        int commodityId = mapper.readTree(jsonData).get("id").asInt();
        int providerId = mapper.readTree(jsonData).get("providerId").asInt();
        if(isIdExist(commodityId))
            throw new CustomException("commodity already exists");
        if(!database.isProviderExists(providerId))
            throw new CustomException("provider does not exists");
        var newCommodity = mapper.readValue(jsonData, Commodity.class);
        commodityHashMap.put(commodityId,newCommodity);
//        String jsonString = mapper.writeValueAsString(commodityHashMap.get(1));
//        System.out.println(jsonString);
        return "commodity added.";
    }

    private boolean isIdExist(int id){
        return commodityHashMap.containsKey(id);
    }
//    private String getCommoditiesList(){
//
//    }

}
