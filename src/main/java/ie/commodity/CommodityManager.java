package ie.commodity;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ie.Baloot;
import ie.CustomException;
import java.lang.reflect.Array;
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
            throw new CustomException("commodity already exists");
        if(!database.isProviderExists(providerId))
            throw new CustomException("provider does not exists");
        var newCommodity = mapper.readValue(jsonData, Commodity.class);
        commodityHashMap.put(commodityId,newCommodity);

        return "commodity added.";
    }

    private boolean isIdExist(int id){
        return commodityHashMap.containsKey(id);
    }
    public JsonNode getCommoditiesList() throws JsonProcessingException {

        ArrayList<JsonNode> JsonNodesList=new ArrayList<>();
        for(Commodity c: commodityHashMap.values()){
            JsonNode s = (ObjectNode) mapper.valueToTree(c);
            JsonNodesList.add(s);

        }
        JsonNode jsonNode = mapper.createObjectNode();
        ((ObjectNode) jsonNode).set("commoditiesList",mapper.convertValue(mapper.valueToTree(JsonNodesList),JsonNode.class));
        //System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode));
        return jsonNode;
    }

}
