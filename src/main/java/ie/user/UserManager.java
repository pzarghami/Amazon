package ie.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.Baloot;
import ie.CustomException;

import java.util.HashMap;



public class UserManager {
    private final HashMap<String, User> userMap;
    private final Baloot database;
    private final ObjectMapper mapper;

    public UserManager (Baloot database) {
        mapper = new ObjectMapper();
        this.database = database;
        userMap = new HashMap<>();
    }

    public String updateOrAddUser(String jsonData) throws  JsonProcessingException {
        String username = mapper.readTree(jsonData).get("username").asText();
        if(isUsernameValid(username,false)){
            updateUser(username,jsonData);
            return "user updated.";
        }else{
            addUser(username,jsonData);
            return "user added.";
        }
    }

    private void updateUser(String username, String jsonData) throws JsonProcessingException{
        mapper.readerForUpdating(userMap.get(username)).readValue(jsonData);
    }

    private void addUser(String username,String jsonData) throws JsonProcessingException{
        var newUser = mapper.readValue(jsonData, User.class);
        userMap.put(username,newUser);
    }

    public String addToBuyList(String jsonData)throws JsonProcessingException, CustomException {
        var jsonNode=mapper.readTree(jsonData);
        String username = jsonNode.get("username").asText();
        int commodityId = jsonNode.get("commodityId").asInt();
        var user =getElement(username);
        user.addToBuyList(commodityId);
        database.buy(commodityId);
        return "added to buy list.";
    }

    public String removeFromBuyList(String jsonData)throws JsonProcessingException, CustomException{
        var jsonNode=mapper.readTree(jsonData);
        String username = jsonNode.get("username").asText();
        int commodityId = jsonNode.get("commodityId").asInt();
        var user = getElement(username);
        user.removeFromBuyList(commodityId);
        database.canselBuying(commodityId);
        return "removed from watch list.";
    }

    public boolean isUsernameValid(String username,boolean isJsonFile) throws JsonProcessingException {
        if(!isJsonFile)
            return userMap.containsKey(username);
        String user = mapper.readTree(username).get("username").asText();
        return userMap.containsKey(user);
    }


    public User getElement(String username) throws CustomException {
        if (userMap.containsKey(username)) {
            return userMap.get(username);
        }
        throw new CustomException("username not valid");
    }

}
