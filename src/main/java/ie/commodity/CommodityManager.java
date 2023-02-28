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

    public boolean buy(int commodityId)throws CustomException{
        if(!isIdExist(commodityId))
            throw new CustomException("commodity does not exist");
        var commodity=commodityHashMap.get(commodityId);
        commodity.buy();
        return true;
    }

    public float addRate(String jsonData) throws JsonProcessingException,CustomException {
        int commodityId = mapper.readTree(jsonData).get("commodityId").asInt();
        int rate = mapper.readTree(jsonData).get("rate").asInt();
        String username = mapper.readTree(jsonData).get("username").asText();
        if(rate >10 || rate <1)
            throw new CustomException("rate is out of range.");
        if(!isIdExist(commodityId))
            throw new CustomException("commodity does not exist");

        return commodityHashMap.get(commodityId).addRate(username,rate);
    }

    private boolean isIdExist(int id){
        return commodityHashMap.containsKey(id);
    }
//    private String getCommoditiesList(){
//
//    }

}
