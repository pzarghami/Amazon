package ie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.exeption.CustomException;
import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


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
    //Test for rateCommodity
    @Test
    public void rateCommodityHappyPathTest() throws JsonProcessingException {
        baloot.RunCommand("rateCommodity", "{\"username\": \"FarzinAsadi\", \"commodityId\": 1, \"score\": 8}");

        String response = "{\n" + "  \"status\" : \"true\",\n" + "  \"data\" : \""+ Constant.ADD_RATE + "\"\n" + "}";
        assertEquals(response,baloot.getResultCommand());
    }

    @Test
    public void rateCommodityOutOfRangeTest() throws JsonProcessingException {
        baloot.RunCommand("rateCommodity", "{\"username\": \"FarzinAsadi\", \"commodityId\": 1, \"score\": 15}");
        String response = "{\n" + "  \"status\" : \"false\",\n" + "  \"data\" : \""+ Constant.OUT_OF_RANGE_RATE +"\"\n" + "}";
        assertEquals(response,baloot.getResultCommand());
    }

    @Test
    public void rateCommodityUserNameNotFoundTest() throws JsonProcessingException {
        baloot.RunCommand("rateCommodity", "{\"username\": \"Arash_sh\", \"commodityId\": 1, \"score\": 1}");
        String response = "{\n" + "  \"status\" : \"false\",\n" + "  \"data\" : \""+ Constant.USR_NOT_FOUND +"\"\n" + "}";
        assertEquals(response,baloot.getResultCommand());
    }

    @Test
    public void rateCommodityNotFoundTest() throws JsonProcessingException {
        baloot.RunCommand("rateCommodity", "{\"username\": \"FarzinAsadi\", \"commodityId\": 8, \"score\": 1}");
        String response = "{\n" + "  \"status\" : \"false\",\n" + "  \"data\" : \""+ Constant.CMD_NOT_FOUND +"\"\n" + "}";
        assertEquals(response,baloot.getResultCommand());
    }
    //Test for getCommoditiesByCategory
    @Test
    public void getCommoditiesBtCatHappyPathTest() throws JsonProcessingException {
        baloot.RunCommand("getCommoditiesByCategory", "{\"category\": \"Tech\"}");
        String response = "{\n" + "  \"status\" : \"true\",\n" + "  \"data\" : {\n" + "    \"commoditiesList\" : [ {\n" + "      \"id\" : 1,\n" + "      \"name\" : \"Phone\",\n" + "      \"providerId\" : 1,\n" + "      \"price\" : 350.0,\n" + "      \"categories\" : [ \"Phone\", \"Tech\" ],\n" + "      \"rating\" : 0.0\n" + "    }, {\n" + "      \"id\" : 3,\n" + "      \"name\" : \"SmartPen\",\n" + "      \"providerId\" : 1,\n" + "      \"price\" : 30.0,\n" + "      \"categories\" : [ \"School\", \"Tech\" ],\n" + "      \"rating\" : 0.0\n" + "    } ]\n" + "  }\n" + "}";

        assertEquals(response,baloot.getResultCommand());
    }

    @Test
    public void getCommoditiesBtCatEmptyListTest() throws JsonProcessingException {
        baloot.RunCommand("getCommoditiesByCategory", "{\"category\": \"Bed\"}");
        String response = "{\n" + "  \"status\" : \"true\",\n" + "  \"data\" : {\n" + "    \"commoditiesList\" : [ ]\n" + "  }\n" + "}";
        assertEquals(response,baloot.getResultCommand());
    }
    //Test for addToBuyList
    @Test
    public void addToBuyListHappyPathTest() throws JsonProcessingException {
        baloot.RunCommand("addToBuyList", "{\"username\": \"FarzinAsadi\",\"commodityId\": 1 }");
        String response = "{\n" + "  \"status\" : \"true\",\n" + "  \"data\" : \""+ Constant.ADD_TO_BUYLIST +"\"\n" + "}";
        assertEquals(response,baloot.getResultCommand());
    }

    @Test
    public void addToBuyListUserNotFoundTest() throws JsonProcessingException {
        baloot.RunCommand("addToBuyList", "{\"username\": \"Negrmg\",\"commodityId\": 1 }");
        String response = "{\n" + "  \"status\" : \"false\",\n" + "  \"data\" : \""+ Constant.USR_NOT_FOUND +"\"\n" + "}";
        assertEquals(response,baloot.getResultCommand());
    }

    @Test
    public void addToBuyListCommodityNotFoundTest() throws JsonProcessingException {
        baloot.RunCommand("addToBuyList", "{\"username\": \"FarzinAsadi\",\"commodityId\": 10 }");
        String response = "{\n" + "  \"status\" : \"false\",\n" + "  \"data\" : \""+Constant.CMD_NOT_FOUND+"\"\n" + "}";
        assertEquals(response,baloot.getResultCommand());
    }
    @Test
    public void addToBuyListCommodityNotEnoughTest() throws JsonProcessingException {
        baloot.RunCommand("addToBuyList", "{\"username\": \"FarzinAsadi\",\"commodityId\": 1 }");
        baloot.RunCommand("addToBuyList", "{\"username\": \"Prmidaghm\",\"commodityId\": 1 }");
        String response = "{\n" + "  \"status\" : \"false\",\n" + "  \"data\" : \""+Constant.LACK_OF_COMMODITY+"\"\n" + "}";
        assertEquals(response,baloot.getResultCommand());
    }
    @Test
    public void addToBuyListCommonCommodityTest() throws JsonProcessingException {
        baloot.RunCommand("addToBuyList", "{\"username\": \"Prmidaghm\",\"commodityId\": 2 }");
        baloot.RunCommand("addToBuyList", "{\"username\": \"Prmidaghm\",\"commodityId\": 2 }");
        String response = "{\n" + "  \"status\" : \"false\",\n" + "  \"data\" : \""+Constant.DUPLICATE_COMMODITY+"\"\n" + "}";
        assertEquals(response,baloot.getResultCommand());
    }
    //Test for getCommodityById
    @Test
    public void getCommodityByIdHappyPathTest() throws JsonProcessingException {
        baloot.RunCommand("getCommodityById", "{\"id\": 1 }");
        String response = "{\n" + "  \"status\" : \"true\",\n" + "  \"data\" : {\n" + "    \"id\" : 1,\n" + "    \"name\" : \"Phone\",\n" + "    \"providerId\" : 1,\n" + "    \"price\" : 350.0,\n" + "    \"categories\" : [ \"Phone\", \"Tech\" ],\n" + "    \"rating\" : 0.0\n" + "  }\n" + "}";
        assertEquals(response,baloot.getResultCommand());
    }
    @Test
    public void getCommodityByIdIdNotFound() throws JsonProcessingException {
        baloot.RunCommand("getCommodityById", "{\"id\": 7 }");
        String response = "{\n" + "  \"status\" : \"false\",\n" + "  \"data\" : \""+Constant.CMD_NOT_FOUND+"\"\n" + "}";
        assertEquals(response,baloot.getResultCommand());
    }




}
