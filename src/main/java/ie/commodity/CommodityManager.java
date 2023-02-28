package ie.commodity;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ie.Baloot;
import ie.Constant;
import ie.CustomException;

import java.util.ArrayList;
import java.util.HashMap;

public class CommodityManager {

    private final HashMap<Integer, Commodity> commodityHashMap;
    private final ArrayList<String> commoditiesList;
    private final Baloot database;
    private final ObjectMapper mapper;


    @JsonGetter(value = "commoditiesList")
    private ArrayList<String> getCommodities() {return this.commoditiesList;}

    public CommodityManager (Baloot database) {
        mapper = new ObjectMapper();
        this.database = database;
        commodityHashMap = new HashMap<>();
        this.commoditiesList=new ArrayList<>();

    }

    public String addCommodity(String jsonData) throws JsonProcessingException , CustomException{
        int commodityId = mapper.readTree(jsonData).get("id").asInt();
        int providerId = mapper.readTree(jsonData).get("providerId").asInt();
        if(isIdExist(commodityId))
            throw new CustomException(Constant.NO_NEW_COMMODITY);
        if(!database.isProviderExists(providerId))
            throw new CustomException(Constant.PROVIDER_NOT_FOUND);
        var newCommodity = mapper.readValue(jsonData, Commodity.class);
        commodityHashMap.put(commodityId,newCommodity);

        return Constant.COMMODITY_ADD;
    }

    public void buy(int commodityId)throws CustomException{
        if(!isIdExist(commodityId))
            throw new CustomException(Constant.CMD_NOT_FOUND);
        var commodity=commodityHashMap.get(commodityId);
        commodity.buy();
    }

    public void cancelBuying(int commodityId)throws CustomException{
        if(!isIdExist(commodityId))
            throw new CustomException(Constant.CMD_NOT_FOUND);
        var commodity=commodityHashMap.get(commodityId);
        commodity.cancelBuying();
    }

    public float addRate(String jsonData) throws JsonProcessingException,CustomException {
        int commodityId = mapper.readTree(jsonData).get("commodityId").asInt();
        int rate = mapper.readTree(jsonData).get("score").asInt();
        String username = mapper.readTree(jsonData).get("username").asText();
        if(rate >10 || rate <1)
            throw new CustomException(Constant.OUT_OF_RANGE_RATE);
        if(!isIdExist(commodityId))
            throw new CustomException(Constant.CMD_NOT_FOUND);

        return commodityHashMap.get(commodityId).addRate(username,rate);
    }

    private boolean isIdExist(int id){
        return commodityHashMap.containsKey(id);
    }
    public int getProviderId(String jsonData)throws JsonProcessingException, CustomException{
        int commodityId = mapper.readTree(jsonData).get("commodityId").asInt();
        return commodityHashMap.get(commodityId).getProvideId();

    }
    public JsonNode getCommoditiesList() throws JsonProcessingException {

        ArrayList<JsonNode> JsonNodesList=new ArrayList<>();
        for(Commodity c: commodityHashMap.values()){
            JsonNode s = (ObjectNode) mapper.valueToTree(c);
            JsonNodesList.add(s);

        }
        JsonNode jsonNode = mapper.createObjectNode();
        ((ObjectNode) jsonNode).set("commoditiesList",mapper.convertValue(mapper.valueToTree(JsonNodesList),JsonNode.class));

        return jsonNode;
    }
    public JsonNode getCommoditiesIdData(String jsonData) throws JsonProcessingException, CustomException {
        int commodityId=mapper.readTree(jsonData).get("id").asInt();
        if(!isIdExist(commodityId))
            throw new CustomException(Constant.CMD_NOT_FOUND);
        var commodity= commodityHashMap.get(commodityId);
        JsonNode s = (ObjectNode) mapper.valueToTree(commodity);
        ((ObjectNode) s).remove("inStock");
        return s;
    }
    public JsonNode getCommoditiesByCategory(String jsonData) throws JsonProcessingException {
        ArrayList<JsonNode> JsonNodesList=new ArrayList<>();
        String category=mapper.readTree(jsonData).get("category").asText();
        for(Commodity c: commodityHashMap.values()){
            if(c.isYourCategory(category)){
                JsonNode s = (ObjectNode) mapper.valueToTree(c);
                ((ObjectNode) s).remove("inStock");
                JsonNodesList.add(s);
            }
        }
        JsonNode jsonNode = mapper.createObjectNode();
        ((ObjectNode) jsonNode).set("commoditiesList",mapper.convertValue(mapper.valueToTree(JsonNodesList),JsonNode.class));
        return jsonNode;
    }

}
