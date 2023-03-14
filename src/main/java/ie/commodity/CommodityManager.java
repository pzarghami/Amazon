package ie.commodity;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ie.*;
import ie.provider.ProviderManager;

import java.util.ArrayList;
import java.util.HashMap;

public class CommodityManager extends Manager<Commodity> {

    private static CommodityManager instance;
    private final HashMap<Integer, Commodity> commodityHashMap;
    private final ArrayList<String> commoditiesList;

    private final ObjectMapper mapper;
    private final JsonHandler<Commodity> jsonMapper;


    @JsonGetter(value = "commoditiesList")
    private ArrayList<String> getCommodities() {return this.commoditiesList;}
    public static CommodityManager getInstance() {
        if (instance == null) {
            instance = new CommodityManager();
        }
        return instance;
    }
    public CommodityManager () {
        mapper = new ObjectMapper();
        jsonMapper = new CommodityJsonHandler();

        commodityHashMap = new HashMap<>();

        this.commoditiesList=new ArrayList<>();

    }

    @Override
    public String addElement(Commodity newObject) throws CustomException {
        var objectId = Integer.toString(newObject.getId());
        if (isIdValid(objectId)) {
            throw new CustomException("ObjectAlreadyExists");
        }
        this.objectMap.put(objectId, newObject);
        ProviderManager.getInstance().getElementById(Integer.toString(newObject.getProvideId())).addToCommoditiesList(Integer.toString(newObject.getId()));
        return objectId;
    }

    @Override
    public String updateElement(Commodity newObject) throws CustomException {
        return null;
    }

    public ArrayList<String> addElementsJson(String jsonData) throws JsonProcessingException, CustomException {
        var objectIds = new ArrayList<String>();
        for (var deserializedObject : jsonMapper.deserializeList(jsonData)) {
            objectIds.add(addElement(deserializedObject));
        }
        return objectIds;
    }

    public String addCommodity(String jsonData) throws JsonProcessingException , CustomException{
        int commodityId = mapper.readTree(jsonData).get("id").asInt();
        int providerId = mapper.readTree(jsonData).get("providerId").asInt();
        if(isIdExist(commodityId))
            throw new CustomException(Constant.NO_NEW_COMMODITY);
        if(!ProviderManager.getInstance().isIDValid(providerId))
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

    public boolean isIdExist(int id){
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
