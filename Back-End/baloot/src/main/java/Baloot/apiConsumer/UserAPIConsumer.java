package Baloot.apiConsumer;

import Baloot.Exeption.CustomException;
import Baloot.model.User;
import Baloot.repository.UserRepo;
import com.fasterxml.jackson.databind.JsonNode;

public class UserAPIConsumer extends APIConsumer {
    public UserAPIConsumer(String apiUrl){
        this.apiUrl = apiUrl;
    }

    protected void loadRepo(JsonNode nodeArray) {
        var repo = UserRepo.getInstance();
        for(var node : nodeArray){
            try{
                var newUser = makeNewUser(node);
                repo.addElement(newUser);
            }catch (CustomException e){
                //ignore
            }
        }
    }
    private User makeNewUser(JsonNode node)throws CustomException{
        String username = node.get("username").asText();
        String password = node.get("password").asText();
        String email = node.get("email").asText();
        String birthdate = node.get("birthDate").asText();
        String address = node.get("address").asText();
        int credit = node.get("credit").asInt();
        return new User(username,password,email,birthdate,address,credit);
    }
}
