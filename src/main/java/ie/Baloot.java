package ie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ie.commodity.CommodityManager;
import ie.user.UserManager;
import ie.provider.ProviderManager;
import java.util.ArrayList;
import java.util.Objects;

public class Baloot {
    private final UserManager userManager;
    private final ProviderManager providerManager;
    private final CommodityManager commodityManager;
    private final ObjectMapper mapper;
    private final JsonNode jsonResNode;

    String resultCommand;

    public Baloot() {
        this.userManager = new UserManager(this);
        this.providerManager = new ProviderManager(this);
        this.commodityManager = new CommodityManager(this);
        this.mapper = new ObjectMapper();
        this.jsonResNode = mapper.createObjectNode();

    }

    public void RunCommand(String command, String data) throws JsonProcessingException {
        try {

            if (Objects.equals(command, "addUser")) {
                displayRes("true", userManager.updateOrAddUser(data), null);

            } else if (Objects.equals(command, "addProvider")) {
                displayRes("true", providerManager.updateOrAddProvider(data), null);

            } else if (Objects.equals(command, "addCommodity")) {
                displayRes("true", commodityManager.addCommodity(data), null);

            } else if (Objects.equals(command, "getCommoditiesList")) {
                displayRes("true", "", commodityManager.getCommoditiesList());

            } else if (Objects.equals(command, "rateCommodity")) {
                displayRes("true", addRate(data), null);

            } else if (Objects.equals(command, "addToBuyList")) {
                displayRes("true", userManager.addToBuyList(data), null);

            } else if (Objects.equals(command, "removeFromBuyList")) {
                displayRes("true", userManager.removeFromBuyList(data), null);

            } else if (Objects.equals(command, "getCommodityById")) {
                displayRes("true", "", commodityManager.getCommoditiesIdData(data));

            } else if (Objects.equals(command, "getCommoditiesByCategory")) {
                displayRes("true", "", commodityManager.getCommoditiesByCategory(data));

            } else if (Objects.equals(command, "getBuyList")) {
                displayRes("true", "", userManager.getBuyList(data));

            } else {
                throw new CustomException("InvalidCommand");
            }

        } catch (Exception e) {
            displayRes("false", e.getMessage(), null);
        }
    }

    public boolean isProviderExists(int id) {
        return providerManager.isIDValid(id);
    }

    public void displayRes(String status, String dataValue, JsonNode j) throws JsonProcessingException {
        ((ObjectNode) jsonResNode).put("status", status);
        if (j == null)
            ((ObjectNode) jsonResNode).put("data", dataValue);
        else {
            ((ObjectNode) jsonResNode).put("data", mapper.convertValue(mapper.valueToTree(j), JsonNode.class));
        }
        resultCommand = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonResNode);
        System.out.println(resultCommand);
    }

    public void buy(int commodityId) throws CustomException {
        commodityManager.buy(commodityId);
    }

    public void cancelBuying(int commodityId) throws CustomException {
        commodityManager.cancelBuying(commodityId);
    }

    private String addRate(String jsonData) throws JsonProcessingException, CustomException {
        if (!userManager.isUsernameValid(jsonData, true))
            throw new CustomException(Constant.USR_NOT_FOUND);
        float averageRate = commodityManager.addRate(jsonData);
        int providerId = commodityManager.getProviderId(jsonData);
        int commodityId = mapper.readTree(jsonData).get("commodityId").asInt();
        providerManager.setAverageRate(providerId, averageRate, commodityId);
        return Constant.ADD_RATE;
    }

    public ArrayList<JsonNode> getBuyListInfo(ArrayList<Integer> ids) throws CustomException, JsonProcessingException {
        ArrayList<JsonNode> JsonNodesList = new ArrayList<>();
        for (int i : ids) {
            String jsonData = "{ \"id\": " + i + "}";
            JsonNodesList.add(commodityManager.getCommoditiesIdData(jsonData));
        }
        return JsonNodesList;
    }

    public String getResultCommand() {
        return this.resultCommand;
    }

}