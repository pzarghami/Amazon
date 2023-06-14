package Baloot.apiConsumer;

import Baloot.Exeption.CustomException;
import Baloot.model.Discount;
import Baloot.repository.CommodityRepo;
import Baloot.repository.DiscountRepo;
import com.fasterxml.jackson.databind.JsonNode;

import java.sql.SQLException;

public class DiscountApiConsumer extends APIConsumer{

    public DiscountApiConsumer(String apiUrl){this.apiUrl = apiUrl;}
    @Override
    protected void loadRepo(JsonNode nodeArray) {
        var repo = DiscountRepo.getInstance();
        for(var node : nodeArray){
            try {
                var newDiscount = makeNewDiscount(node);
                repo.addElement(newDiscount);
            }catch (CustomException | SQLException e){
                //ignore
            }
        }
    }
    private Discount makeNewDiscount(JsonNode node){
        String code = node.get("discountCode").asText();
        int amount = node.get("discount").asInt();
        return new Discount(code, amount);
    }
}
