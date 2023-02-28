package ie;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ie.commodity.CommodityManager;
import ie.user.UserManager;
import ie.provider.ProviderManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;


public class Baloot {
    private final UserManager userManager;
    private final ProviderManager providerManager;
    private final CommodityManager commodityManager;
    private final ObjectMapper mapper;
    private final JsonNode jsonResNode;


    public Baloot(){
        this.userManager = new UserManager(this);
        this.providerManager = new ProviderManager(this);
        this.commodityManager= new CommodityManager(this);
        this.mapper=new ObjectMapper();
        this.jsonResNode=mapper.createObjectNode();
    }
    public void RunCommand(String command,String data) throws JsonProcessingException {
        try{

            if(Objects.equals(command,"addUser")){
                displayRes("true",userManager.updateOrAddUser(data),null);

            }
            else if(Objects.equals(command,"addProvider")){
                displayRes("true",providerManager.updateOrAddProvider(data),null);
            }
            else if(Objects.equals(command,"addCommodity")){
                displayRes("true",commodityManager.addCommodity(data),null);
//todo
            }
            else if(Objects.equals(command,"getCommoditiesList")){
                displayRes("true","",commodityManager.getCommoditiesList());

//todo
            }
            else if(Objects.equals(command,"rateCommodity")){
                displayRes("true",addRate(data),null);
//todo
            }
            else if(Objects.equals(command,"addToBuyList")){
                displayRes("true",userManager.addToBuyList(data),null);

            }
            else if(Objects.equals(command,"removeFromBuyList")){
                displayRes("true",userManager.removeFromBuyList(data),null);
//todo
            }
            else if(Objects.equals(command,"getCommodityById")){
                displayRes("true","",commodityManager.getCommoditiesIdData(data));
//todo
            }
            else if(Objects.equals(command,"getCommoditiesByCategory")){
                displayRes("true","",commodityManager.getCommoditiesByCategory(data));
//todo
            }
            else if(Objects.equals(command,"getBuyList")){
                System.out.println(data);
//todo
            }
            else{
                throw new CustomException("InvalidCommand");
//todo
            }

//todo

        } catch (CustomException e) {
            displayRes("false",e.getMessage(),null);
//todo
        } catch (Exception e) {
            displayRes("false",e.getMessage(),null);
        }

    }

    public boolean isProviderExists(int id){
        return providerManager.isIDValid(id);
    }

    public void displayRes(String status,String dataValue, JsonNode j) throws JsonProcessingException {
        ((ObjectNode) jsonResNode).put("status",status);
        if(j==null)
            ((ObjectNode) jsonResNode).put("data",dataValue);
        else{
            ((ObjectNode) jsonResNode).put("data",mapper.convertValue(mapper.valueToTree(j),JsonNode.class));
        }
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonResNode));
    }

    public void buy(int commodityId)throws CustomException{
        commodityManager.buy(commodityId);
    }

    public void canselBuying(int commodityId)throws CustomException{
        commodityManager.canselBuying(commodityId);
    }

    private String addRate(String jsonData)throws JsonProcessingException,CustomException{
        if(!userManager.isUsernameValid(jsonData,true))
            throw new CustomException("username does not exist");
        float averageRate = commodityManager.addRate(jsonData);
        int providerId=commodityManager.getProviderId(jsonData);
        int commodityId=mapper.readTree(jsonData).get("commodityId").asInt();
        providerManager.setAverageRate(providerId,averageRate,commodityId);
        return "rate added.";
    }


}