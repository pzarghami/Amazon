package ie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ie.comment.CommentManager;
import ie.commodity.CommodityManager;
import ie.exeption.CustomException;
import ie.user.UserManager;
import ie.provider.ProviderManager;

import java.util.ArrayList;

import org.jsoup.Jsoup;


public class Baloot {
    private final UserManager userManager;
    private final ProviderManager providerManager;
    private final CommodityManager commodityManager;
    private final CommentManager commentManager;
    private final ObjectMapper mapper;
    private final JsonNode jsonResNode;

    String resultCommand;

    public static ArrayList<String> userIds;
    public static ArrayList<String> providerIds;
    public static ArrayList<String> commoditiesIds;
    public static ArrayList<String> commentIds;

    public Baloot() {
        this.userManager = UserManager.getInstance();
        this.providerManager = ProviderManager.getInstance();
        this.commodityManager = CommodityManager.getInstance();
        this.commentManager = CommentManager.getInstance();
        this.mapper = new ObjectMapper();
        this.jsonResNode = mapper.createObjectNode();

    }
    public void fetchData() throws CustomException {
        try {
            userIds = userManager.addElementsJson(Jsoup.connect(Constant.FETCH_DATA_ADDR.USER).ignoreContentType(true).execute().body());
            providerIds = providerManager.addElementsJson(Jsoup.connect(Constant.FETCH_DATA_ADDR.PROVIDER).ignoreContentType(true).execute().body());
            commoditiesIds = commodityManager.addElementsJson(Jsoup.connect(Constant.FETCH_DATA_ADDR.COMMODITIES).ignoreContentType(true).execute().body());
            commentIds = commentManager.addElementsJson(Jsoup.connect(Constant.FETCH_DATA_ADDR.COMMENTS).ignoreContentType(true).execute().body());
        } catch (Exception e) {
            throw new CustomException("DataFetchingFailed");
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
        commodityManager.buy(String.valueOf(commodityId));
    }

    public void cancelBuying(int commodityId) throws CustomException {
        commodityManager.cancelBuying(String.valueOf(commodityId));
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
    public void removeDatabase() {
        commodityManager.removeElements(null);
        providerManager.removeElements(null);
        userManager.removeElements(null);
        commentManager.removeElements(null);
    }


}