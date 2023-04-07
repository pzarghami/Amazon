package ie.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ie.Manager;
import ie.JsonHandler;
import ie.exeption.CustomException;
import ie.Constant;
import ie.commodity.CommodityManager;

import java.util.ArrayList;


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
        String commodityId = jsonNode.get("commodityId").asText();
        var user =getElementById(username);
        CommodityManager.getInstance().buy(commodityId);
        user.addToUserBuyList(commodityId);
        return Constant.ADD_TO_BUYLIST;
    }

    public String addToBuyList(String username, String commodityId) throws CustomException {
        var user =getElementById(username);
        CommodityManager.getInstance().buy(commodityId);
        user.addToUserBuyList(commodityId);
        return Constant.ADD_TO_BUYLIST;
    }



    public boolean isEmailExists(String email){
        for(User user: objectMap.values()){
            if(user.isYourEmail(email))
                return true;
        }
        return false;
    }

}
