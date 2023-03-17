package ie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.exeption.CustomException;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


public class BalootTest {
    Baloot baloot;
    Document responseBody = null;

    public BalootTest() {
        this.baloot = new Baloot();

    }
    @Before
    public void setup() throws CustomException {
        baloot = new Baloot();
        baloot.fetchData();
        baloot.startServer();
    }

    @After
    public void tearDown() {
        baloot.removeDatabase();
        baloot.stopServer();
        baloot = null;
        responseBody = null;
    }
    public void assert404Response(int code) {
        assertEquals(404, code);
    }
    public void assert403Response(int code) {
        assertEquals(403, code);
    }
    public void assertHtmlValue(String htmlElementId, String expectedText) throws IOException {
        assertEquals(expectedText, responseBody.getElementById(htmlElementId).text());
    }
    public void assertHtmlValue(String htmlElementName, int elementIndex, String expectedText) throws IOException {
        assertEquals(expectedText, responseBody.select(htmlElementName).get(elementIndex).text());
    }
    public void hadUnknownPage() throws IOException {
        Jsoup.connect(Constant.Server.BASE + "/"+Constant.Testing.UNKNOWN).execute();

    }
    //Test for rating commodities.
    @Test
    public void AddRateCommoditySuccessTest() throws IOException {
        var users = Baloot.userIds;
        var commodity = Baloot.commoditiesIds;
        Jsoup.connect(Constant.Server.BASE + "/rateCommodity/" + users.get(0) + '/' + commodity.get(1) + "/8").execute();
        Jsoup.connect(Constant.Server.BASE + "/rateCommodity/" + users.get(1) + '/' + commodity.get(1) + "/9").execute();
        responseBody = Jsoup.connect(Constant.Server.BASE + "/commodities/" + commodity.get(1)).execute().parse();
        assertHtmlValue("rating", "Rating: 8.0");

    }
    @Test
    public void UpdateSameUserRateCommoditySuccessTest() throws IOException {
        var users = Baloot.userIds;
        var commodity = Baloot.commoditiesIds;
        Jsoup.connect(Constant.Server.BASE + "/rateCommodity/" + users.get(0) + '/' + commodity.get(1) + "/8").execute();
        Jsoup.connect(Constant.Server.BASE + "/rateCommodity/" + users.get(1) + '/' + commodity.get(1) + "/9").execute();
        // updating rate
        Jsoup.connect(Constant.Server.BASE + "/rateCommodity/" + users.get(1) + '/' + commodity.get(1) + "/3").execute();
        responseBody = Jsoup.connect(Constant.Server.BASE + "/commodities/" + commodity.get(1)).execute().parse();
        assertHtmlValue("rating", "Rating: 6.0");

    }
    public void rateCommodityFromUnknownUser() throws IOException {
        var users = Baloot.userIds;
        var commodity = Baloot.commoditiesIds;
        Jsoup.connect(Constant.Server.BASE + "/rateCommodity/" + Constant.Testing.UNKNOWN + '/' + commodity.get(1) + "/8").execute();
    }
    public void rateCommodityFromUnknownCommodity() throws IOException {
        var users = Baloot.userIds;
        var commodity = Baloot.commoditiesIds;
        Jsoup.connect(Constant.Server.BASE + "/rateCommodity/" + users.get(0) + '/' + Constant.Testing.UNKNOWN + "/8").execute();
    }
    public void rateCommodityWithInvalidRate() throws IOException {
        var users = Baloot.userIds;
        var commodity = Baloot.commoditiesIds;
        Jsoup.connect(Constant.Server.BASE + "/rateCommodity/" + users.get(1) + '/' + commodity.get(1) + "/20").execute();
    }

    @Test
    public void rateCommodityFromUnknownUserTest(){
        HttpStatusException e = assertThrows(HttpStatusException.class, this::rateCommodityFromUnknownUser);
        assert403Response(e.getStatusCode());
    }

    @Test
    public void rateCommodityFromUnknownCommodityTest(){
        HttpStatusException e = assertThrows(HttpStatusException.class, this::rateCommodityFromUnknownCommodity);
        assert403Response(e.getStatusCode());
    }
    @Test
    public void rateCommodityWithInvalidRateTest(){
        HttpStatusException e = assertThrows(HttpStatusException.class, this::rateCommodityWithInvalidRate);
        assert403Response(e.getStatusCode());
    }
    @Test
    public void hadUnknownPageTest(){
        HttpStatusException e = assertThrows(HttpStatusException.class, this::hadUnknownPage);
        assert404Response(e.getStatusCode());
    }

//    //Test for rateCommodity
//    @Test
//    public void rateCommodityHappyPathTest() throws JsonProcessingException {
//        baloot.RunCommand("rateCommodity", "{\"username\": \"FarzinAsadi\", \"commodityId\": 1, \"score\": 8}");
//
//        String response = "{\n" + "  \"status\" : \"true\",\n" + "  \"data\" : \""+ Constant.ADD_RATE + "\"\n" + "}";
//        assertEquals(response,baloot.getResultCommand());
//    }
//
//    @Test
//    public void rateCommodityOutOfRangeTest() throws JsonProcessingException {
//        baloot.RunCommand("rateCommodity", "{\"username\": \"FarzinAsadi\", \"commodityId\": 1, \"score\": 15}");
//        String response = "{\n" + "  \"status\" : \"false\",\n" + "  \"data\" : \""+ Constant.OUT_OF_RANGE_RATE +"\"\n" + "}";
//        assertEquals(response,baloot.getResultCommand());
//    }
//
//    @Test
//    public void rateCommodityUserNameNotFoundTest() throws JsonProcessingException {
//        baloot.RunCommand("rateCommodity", "{\"username\": \"Arash_sh\", \"commodityId\": 1, \"score\": 1}");
//        String response = "{\n" + "  \"status\" : \"false\",\n" + "  \"data\" : \""+ Constant.USR_NOT_FOUND +"\"\n" + "}";
//        assertEquals(response,baloot.getResultCommand());
//    }
//
//    @Test
//    public void rateCommodityNotFoundTest() throws JsonProcessingException {
//        baloot.RunCommand("rateCommodity", "{\"username\": \"FarzinAsadi\", \"commodityId\": 8, \"score\": 1}");
//        String response = "{\n" + "  \"status\" : \"false\",\n" + "  \"data\" : \""+ Constant.CMD_NOT_FOUND +"\"\n" + "}";
//        assertEquals(response,baloot.getResultCommand());
//    }
//    //Test for getCommoditiesByCategory
//    @Test
//    public void getCommoditiesBtCatHappyPathTest() throws JsonProcessingException {
//        baloot.RunCommand("getCommoditiesByCategory", "{\"category\": \"Tech\"}");
//        String response = "{\n" + "  \"status\" : \"true\",\n" + "  \"data\" : {\n" + "    \"commoditiesList\" : [ {\n" + "      \"id\" : 1,\n" + "      \"name\" : \"Phone\",\n" + "      \"providerId\" : 1,\n" + "      \"price\" : 350.0,\n" + "      \"categories\" : [ \"Phone\", \"Tech\" ],\n" + "      \"rating\" : 0.0\n" + "    }, {\n" + "      \"id\" : 3,\n" + "      \"name\" : \"SmartPen\",\n" + "      \"providerId\" : 1,\n" + "      \"price\" : 30.0,\n" + "      \"categories\" : [ \"School\", \"Tech\" ],\n" + "      \"rating\" : 0.0\n" + "    } ]\n" + "  }\n" + "}";
//
//        assertEquals(response,baloot.getResultCommand());
//    }
//
//    @Test
//    public void getCommoditiesBtCatEmptyListTest() throws JsonProcessingException {
//        baloot.RunCommand("getCommoditiesByCategory", "{\"category\": \"Bed\"}");
//        String response = "{\n" + "  \"status\" : \"true\",\n" + "  \"data\" : {\n" + "    \"commoditiesList\" : [ ]\n" + "  }\n" + "}";
//        assertEquals(response,baloot.getResultCommand());
//    }
//    //Test for addToBuyList
//    @Test
//    public void addToBuyListHappyPathTest() throws JsonProcessingException {
//        baloot.RunCommand("addToBuyList", "{\"username\": \"FarzinAsadi\",\"commodityId\": 1 }");
//        String response = "{\n" + "  \"status\" : \"true\",\n" + "  \"data\" : \""+ Constant.ADD_TO_BUYLIST +"\"\n" + "}";
//        assertEquals(response,baloot.getResultCommand());
//    }
//
//    @Test
//    public void addToBuyListUserNotFoundTest() throws JsonProcessingException {
//        baloot.RunCommand("addToBuyList", "{\"username\": \"Negrmg\",\"commodityId\": 1 }");
//        String response = "{\n" + "  \"status\" : \"false\",\n" + "  \"data\" : \""+ Constant.USR_NOT_FOUND +"\"\n" + "}";
//        assertEquals(response,baloot.getResultCommand());
//    }
//
//    @Test
//    public void addToBuyListCommodityNotFoundTest() throws JsonProcessingException {
//        baloot.RunCommand("addToBuyList", "{\"username\": \"FarzinAsadi\",\"commodityId\": 10 }");
//        String response = "{\n" + "  \"status\" : \"false\",\n" + "  \"data\" : \""+Constant.CMD_NOT_FOUND+"\"\n" + "}";
//        assertEquals(response,baloot.getResultCommand());
//    }
//    @Test
//    public void addToBuyListCommodityNotEnoughTest() throws JsonProcessingException {
//        baloot.RunCommand("addToBuyList", "{\"username\": \"FarzinAsadi\",\"commodityId\": 1 }");
//        baloot.RunCommand("addToBuyList", "{\"username\": \"Prmidaghm\",\"commodityId\": 1 }");
//        String response = "{\n" + "  \"status\" : \"false\",\n" + "  \"data\" : \""+Constant.LACK_OF_COMMODITY+"\"\n" + "}";
//        assertEquals(response,baloot.getResultCommand());
//    }
//    @Test
//    public void addToBuyListCommonCommodityTest() throws JsonProcessingException {
//        baloot.RunCommand("addToBuyList", "{\"username\": \"Prmidaghm\",\"commodityId\": 2 }");
//        baloot.RunCommand("addToBuyList", "{\"username\": \"Prmidaghm\",\"commodityId\": 2 }");
//        String response = "{\n" + "  \"status\" : \"false\",\n" + "  \"data\" : \""+Constant.DUPLICATE_COMMODITY+"\"\n" + "}";
//        assertEquals(response,baloot.getResultCommand());
//    }
//    //Test for getCommodityById
//    @Test
//    public void getCommodityByIdHappyPathTest() throws JsonProcessingException {
//        baloot.RunCommand("getCommodityById", "{\"id\": 1 }");
//        String response = "{\n" + "  \"status\" : \"true\",\n" + "  \"data\" : {\n" + "    \"id\" : 1,\n" + "    \"name\" : \"Phone\",\n" + "    \"providerId\" : 1,\n" + "    \"price\" : 350.0,\n" + "    \"categories\" : [ \"Phone\", \"Tech\" ],\n" + "    \"rating\" : 0.0\n" + "  }\n" + "}";
//        assertEquals(response,baloot.getResultCommand());
//    }
//    @Test
//    public void getCommodityByIdIdNotFound() throws JsonProcessingException {
//        baloot.RunCommand("getCommodityById", "{\"id\": 7 }");
//        String response = "{\n" + "  \"status\" : \"false\",\n" + "  \"data\" : \""+Constant.CMD_NOT_FOUND+"\"\n" + "}";
//        assertEquals(response,baloot.getResultCommand());
//    }




}
