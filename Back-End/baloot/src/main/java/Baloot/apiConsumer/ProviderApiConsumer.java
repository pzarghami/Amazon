package Baloot.apiConsumer;

import Baloot.Exeption.CustomException;
import Baloot.model.Provider;

import Baloot.repository.ProviderRepo;
import com.fasterxml.jackson.databind.JsonNode;

import java.sql.SQLException;

public class ProviderApiConsumer extends APIConsumer{

    public ProviderApiConsumer(String apiUrl){this.apiUrl = apiUrl;}
    @Override
    protected void loadRepo(JsonNode nodeArray) {
        var repo = ProviderRepo.getInstance();
        for(var node : nodeArray){
            try {
                var newProvider = makeNewProvider(node);
                repo.addElement(newProvider);
            }catch (CustomException | SQLException e){
                e.printStackTrace();
            }
        }
    }
    private Provider makeNewProvider(JsonNode node){
        int id = node.get("id").asInt();
        String name = node.get("name").asText();
        String registryDate = node.get("registryDate").asText();
        String image = node.get("image").asText();
        return new Provider(id, name, registryDate, image);
    }
}
