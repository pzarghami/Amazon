package Baloot.apiConsumer;

import Baloot.Exeption.CustomException;
import Baloot.model.Commodity;
import Baloot.model.Provider;
import Baloot.repository.CommodityRepo;
import Baloot.repository.ProviderRepo;
import com.fasterxml.jackson.databind.JsonNode;

import java.sql.SQLException;
import java.util.ArrayList;

public class CommodityApiConsumer extends APIConsumer{
    public CommodityApiConsumer(String apiUrl){this.apiUrl = apiUrl;}

    @Override
    protected void loadRepo(JsonNode nodeArray) {
        var repo = CommodityRepo.getInstance();
        for(var node : nodeArray){
            try {
                var newCommodity = makeNewCommodity(node);
                repo.addElement(newCommodity);
            }catch (CustomException | SQLException e){
                //ignore
            }
        }
    }

    private Commodity makeNewCommodity(JsonNode node) throws CustomException, SQLException {
        String id = node.get("id").asText();
        String name = node.get("name").asText();
        String providerId = node.get("providerId").asText();
        var repo = ProviderRepo.getInstance();
        Provider provider = repo.getElementById(Integer.valueOf(providerId));
        float price = node.get("price").floatValue();
        float rate = node.get("rating").floatValue();
        int inStock = node.get("inStock").asInt();
        String image = node.get("image").asText();
        ArrayList<String> cate = getStringCollection(node.get("categories"));
        return new Commodity(id, name, provider, price, cate, rate, inStock, image);

    }

    private ArrayList<String> getStringCollection(JsonNode node) {
        ArrayList<String> result = new ArrayList<>();
        for (var item : node) {
            result.add(item.asText());
        }
        return result;
    }
}
