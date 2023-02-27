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

    public void updateOrAddUser(String jsonData) throws  JsonProcessingException {
        String username = mapper.readTree(jsonData).get("username").asText();
        if(isIdValid(username)){
            updateUser(username,jsonData);
        }else{
            addUser(username,jsonData);
        }
    }

    private void updateUser(String username, String jsonData) throws JsonProcessingException{
        mapper.readerForUpdating(userMap.get(username)).readValue(jsonData);
    }

    private void addUser(String username,String jsonData) throws JsonProcessingException{
        var newUser = mapper.readValue(jsonData, User.class);
        userMap.put(username,newUser);
    }

    private boolean isIdValid(String username){
        return userMap.containsKey(username);
    }
}
