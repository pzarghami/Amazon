package ie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class BalootTest {
    Baloot baloot;
    private final JsonNode jsonResNode;
    private final ObjectMapper mapper;

    public BalootTest() {
        this.baloot = new Baloot();
        this.mapper=new ObjectMapper();
        this.jsonResNode=mapper.createObjectNode();
    }

    @Before
    public void setup() throws JsonProcessingException {
        new BalootTest();
        baloot.RunCommand("addUser", "{\"username\": \"FarzinAsadi\", \"password\": \"1234\", \"email\": \"farzin.ut.ac.ir\", \"birthDate\": \"2000-24-4\", \"address\": \"Janat-Abad\"}");
        baloot.RunCommand("addUser", "{\"username\": \"Prmidaghm\", \"password\": \"1234\", \"email\": \"parmida.ut.ac.ir\", \"birthDate\": \"2000-24-4\", \"address\": \"AmirAbad\"}");
        baloot.RunCommand("addUser", "{\"username\": \"srsh\", \"password\": \"1234\", \"email\": \"srsh.ut.ac.ir\", \"birthDate\": \"2000-24-4\", \"address\": \"amiraAbad\"}");
        baloot.RunCommand("addUser", "{\"username\": \"athena\", \"password\": \"1234\", \"email\": \"ati.ut.ac.ir\", \"birthDate\": \"2000-24-4\", \"address\": \"enghelab\"}");


        baloot.RunCommand("addProvider", "{\"id\": 1, \"name\": \"Marlon Brando\", \"registryDate\": \"1924-04-03\"}");
        baloot.RunCommand("addProvider", "{\"id\": 2, \"name\": \"Al Pacino\", \"registryDate\": \"1940-04-25\"}");
        baloot.RunCommand("addProvider", "{\"id\": 3, \"name\": \"ames Caan\", \"registryDate\": \"1940-03-26\"}");
        baloot.RunCommand("addProvider", "{\"id\": 4, \"name\": \"Adrien Brody\", \"registryDate\": \"1973-04-14\"}");
        baloot.RunCommand("addProvider", "{\"id\": 5, \"name\": \"Thomas Kretschmann\", \"registryDate\": \"1962-09-08\"}");
        baloot.RunCommand("addProvider", "{\"id\": 6, \"name\": \"Frank Finlay\", \"registryDate\": \"1926-08-06\"}");

        baloot.RunCommand("addCommodity", "{\"id\": 1, \"name\": \"Phone\", \"providerId\": 1,\"price\": 350,\"categories\": [\"Phone\",\"Tech\"], \"rating\":0, \"inStock\": 1 }");
        baloot.RunCommand("addCommodity", "{\"id\": 2, \"name\": \"Chocklate\", \"providerId\": 2,\"price\": 3,\"categories\": [\"Food\"], \"rating\":0, \"inStock\": 5 }");
        baloot.RunCommand("addCommodity", "{\"id\": 3, \"name\": \"SmartPen\", \"providerId\": 1,\"price\": 30,\"categories\": [\"School\",\"Tech\"], \"rating\":0, \"inStock\": 5 }");
    }
    //Test for rateCommodity
    @Test
    public void rateCommodityHappyPathTest() throws JsonProcessingException {
        baloot.RunCommand("rateCommodity", "{\"username\": \"FarzinAsadi\", \"commodityId\": 1, \"score\": 8}");

        String response = "{\n" + "  \"status\" : \"true\",\n" + "  \"data\" : \"rate added.\"\n" + "}";
        assertEquals(response,baloot.getResultCommand());
    }

    @Test
    public void rateCommodityOutOfRangeTest() throws JsonProcessingException {
        baloot.RunCommand("rateCommodity", "{\"username\": \"FarzinAsadi\", \"commodityId\": 1, \"score\": 15}");
        String response = "{\n" + "  \"status\" : \"false\",\n" + "  \"data\" : \"rate is out of range.\"\n" + "}";
        assertEquals(response,baloot.getResultCommand());
    }

    @Test
    public void rateCommodityUserNameNotFoundTest() throws JsonProcessingException {
        baloot.RunCommand("rateCommodity", "{\"username\": \"Arash_sh\", \"commodityId\": 1, \"score\": 1}");
        String response = "{\n" + "  \"status\" : \"false\",\n" + "  \"data\" : \"username does not exist\"\n" + "}";
        assertEquals(response,baloot.getResultCommand());
    }

    @Test
    public void rateCommodityNotFoundTest() throws JsonProcessingException {
        baloot.RunCommand("rateCommodity", "{\"username\": \"FarzinAsadi\", \"commodityId\": 8, \"score\": 1}");
        String response = "{\n" + "  \"status\" : \"false\",\n" + "  \"data\" : \"commodity does not exist\"\n" + "}";
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
        String response = "{\n" + "  \"status\" : \"true\",\n" + "  \"data\" : \"added to buy list.\"\n" + "}";
        assertEquals(response,baloot.getResultCommand());
    }

    @Test
    public void addToBuyListUserNotFoundTest() throws JsonProcessingException {
        baloot.RunCommand("addToBuyList", "{\"username\": \"Negrmg\",\"commodityId\": 1 }");
        String response = "{\n" + "  \"status\" : \"false\",\n" + "  \"data\" : \"username not valid\"\n" + "}";
        assertEquals(response,baloot.getResultCommand());
    }

    @Test
    public void addToBuyListCommodityNotFoundTest() throws JsonProcessingException {
        baloot.RunCommand("addToBuyList", "{\"username\": \"FarzinAsadi\",\"commodityId\": 10 }");
        String response = "{\n" + "  \"status\" : \"false\",\n" + "  \"data\" : \"commodity does not exist\"\n" + "}";
        assertEquals(response,baloot.getResultCommand());
    }
    @Test
    public void addToBuyListCommodityNotEnoughTest() throws JsonProcessingException {
        baloot.RunCommand("addToBuyList", "{\"username\": \"FarzinAsadi\",\"commodityId\": 1 }");
        baloot.RunCommand("addToBuyList", "{\"username\": \"Prmidaghm\",\"commodityId\": 1 }");
        String response = "{\n" + "  \"status\" : \"false\",\n" + "  \"data\" : \"there is no enough commodity.\"\n" + "}";
        assertEquals(response,baloot.getResultCommand());
    }
    @Test
    public void addToBuyListCommonCommodityTest() throws JsonProcessingException {
        baloot.RunCommand("addToBuyList", "{\"username\": \"Prmidaghm\",\"commodityId\": 2 }");
        baloot.RunCommand("addToBuyList", "{\"username\": \"Prmidaghm\",\"commodityId\": 2 }");
        String response = "{\n" + "  \"status\" : \"false\",\n" + "  \"data\" : \"already exists in buy list.\"\n" + "}";
        assertEquals(response,baloot.getResultCommand());
    }




}
