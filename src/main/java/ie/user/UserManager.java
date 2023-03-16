package ie.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import ie.Baloot;
import ie.Manager;
import ie.JsonHandler;
import ie.CustomException;
import ie.Constant;
import ie.commodity.CommodityManager;

import java.util.ArrayList;
import java.util.HashMap;



public class UserManager extends  Manager<User>{
    private final ObjectMapper mapper;
    private final JsonHandler <User> jsonMapper;
    private static UserManager instance;


    public UserManager () {
        mapper = new ObjectMapper();
        jsonMapper = new UserJsonHandler();

    }
    public static UserManager getInstance(){
        if(instance==null)
            instance=new UserManager();
        return instance;
    }
    @Override
    public String addElement(User newObject) throws CustomException {
        var objectId = newObject.getUsername();
        if (isIdValid(objectId)) {
            throw new CustomException("ObjectAlreadyExists");
        }
        this.objectMap.put(objectId, newObject);
        return objectId;
    }

    @Override
    public String updateElement(User newObject) throws CustomException {
        return null;
    }

    public ArrayList<String> addElementsJson(String jsonData) throws JsonProcessingException, CustomException {
        var objectIds = new ArrayList<String>();
        for (var deserializedObject : jsonMapper.deserializeList(jsonData)) {
            objectIds.add(addElement(deserializedObject));
        }
        return objectIds;
    }

    public String updateOrAddUser(String jsonData) throws  JsonProcessingException {
        String username = mapper.readTree(jsonData).get("username").asText();
        if(isUsernameValid(username,false)){
            updateUser(username,jsonData);
            return Constant.USR_UPDATE;
        }else{
            addUser(username,jsonData);
            return Constant.USR_ADD;
        }
    }

    private void updateUser(String username, String jsonData) throws JsonProcessingException{
        mapper.readerForUpdating(objectMap.get(username)).readValue(jsonData);
    }

    private void addUser(String username,String jsonData) throws JsonProcessingException{
        var newUser = mapper.readValue(jsonData, User.class);
        objectMap.put(username,newUser);
    }

    public String addToBuyList(String jsonData)throws JsonProcessingException, CustomException {
        var jsonNode=mapper.readTree(jsonData);
        String username = jsonNode.get("username").asText();
        int commodityId = jsonNode.get("commodityId").asInt();
        var user =getElement(username);
        CommodityManager.getInstance().buy(commodityId);
        user.addToBuyList(commodityId);
        return Constant.ADD_TO_BUYLIST;
    }

    public String addToBuyList(String username, String commodityId) throws CustomException {
        var user =getElement(username);
        user.addToUserBuyList(commodityId);
        CommodityManager.getInstance().buy(Integer.parseInt(commodityId));
        return Constant.ADD_TO_BUYLIST;
    }
    public String removeFromBuyList(String username, String commodityId) throws CustomException {
        var user =getElement(username);
        user.removeFromUserBuyList(commodityId);
        CommodityManager.getInstance().cancelBuying(Integer.parseInt(commodityId));
        return Constant.ADD_TO_BUYLIST;
    }

    public String removeFromBuyList(String jsonData)throws JsonProcessingException, CustomException{
        var jsonNode=mapper.readTree(jsonData);
        String username = jsonNode.get("username").asText();
        int commodityId = jsonNode.get("commodityId").asInt();
        var user = getElement(username);
        user.removeFromBuyList(commodityId);
        CommodityManager.getInstance().cancelBuying(commodityId);
        return Constant.RMV_FROM_BUYLIST;
    }

    public boolean isUsernameValid(String username,boolean isJsonFile) throws JsonProcessingException {
        if(!isJsonFile)
            return objectMap.containsKey(username);
        String user = mapper.readTree(username).get("username").asText();
        return objectMap.containsKey(user);
    }


    public User getElement(String username) throws CustomException {
        if (objectMap.containsKey(username)) {
            return objectMap.get(username);
        }
        throw new CustomException(Constant.USR_NOT_FOUND);
    }
    public JsonNode getBuyList(String jsonData) throws JsonProcessingException, CustomException {

        String username = mapper.readTree(jsonData).get("username").asText();
        if(!isUsernameValid(username,false))
            throw new CustomException(Constant.USR_NOT_FOUND);
        var userBuyList= objectMap.get(username);
        ArrayList<Integer> commodityIdInBuyList= userBuyList.getBuyList();
        JsonNode jsonNode = mapper.createObjectNode();
        //((ObjectNode) jsonNode).set("buyList",mapper.convertValue(CommodityManager.getInstance().getBuyListInfo(commodityIdInBuyList),JsonNode.class));
        return jsonNode;
    }
    public boolean isEmailExists(String email){
        for(User user: objectMap.values()){
            if(user.isYourEmail(email))
                return true;
        }
        return false;
    }

}
