package ie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ie.comment.CommentManager;
import ie.commodity.CommodityManager;
import ie.discount.DiscountManager;
import ie.exeption.CustomException;
import ie.user.User;
import ie.user.UserManager;
import ie.provider.ProviderManager;

import java.util.ArrayList;

import org.jsoup.Jsoup;


public class Baloot {
    private final UserManager userManager;
    private final ProviderManager providerManager;
    private final CommodityManager commodityManager;
    private final CommentManager commentManager;
    private final DiscountManager discountManager;
    private final ObjectMapper mapper;
    private final JsonNode jsonResNode;

    String resultCommand;

    public static ArrayList<String> userIds;
    public static ArrayList<String> providerIds;
    public static ArrayList<String> commoditiesIds;
    public static ArrayList<String> commentIds;
    public static ArrayList<String> discountIds;
    public static User loggedInUser;
    public Baloot() {
        loggedInUser=null;
        this.userManager = UserManager.getInstance();
        this.providerManager = ProviderManager.getInstance();
        this.commodityManager = CommodityManager.getInstance();
        this.commentManager = CommentManager.getInstance();
        this.discountManager = DiscountManager.getInstance();

        this.mapper = new ObjectMapper();
        this.jsonResNode = mapper.createObjectNode();

    }
    public static void loginUser(String username) throws CustomException {
        loggedInUser= UserManager.getInstance().getElementById(username);
    }

    public static boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public static void logoutUser(){
        loggedInUser=null;
    }

    public static void addCredit(int credit){
        loggedInUser.addCredit(credit);
    }

    public void fetchData() throws CustomException {
        try {
            userIds = userManager.addElementsJson(Jsoup.connect(Constant.FETCH_DATA_ADDR.USER).ignoreContentType(true).execute().body());
            providerIds = providerManager.addElementsJson(Jsoup.connect(Constant.FETCH_DATA_ADDR.PROVIDER).ignoreContentType(true).execute().body());
            commoditiesIds = commodityManager.addElementsJson(Jsoup.connect(Constant.FETCH_DATA_ADDR.COMMODITIES).ignoreContentType(true).execute().body());
            commentIds = commentManager.addElementsJson(Jsoup.connect(Constant.FETCH_DATA_ADDR.COMMENTS).ignoreContentType(true).execute().body());
            discountIds = discountManager.addElementsJson(Jsoup.connect("http://5.253.25.110:5000/api/discount").ignoreContentType(true).execute().body());
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