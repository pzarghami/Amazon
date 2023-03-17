package ie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.commodity.CommodityManager;
import ie.exeption.CustomException;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;


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
    @Test
    public void hadUnknownPageTest(){
        HttpStatusException e = assertThrows(HttpStatusException.class, this::hadUnknownPage);
        assert404Response(e.getStatusCode());
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

    //Test for searching commodities according to price
    @Test
    public void searchCommodityByPriceSuccessTest() throws CustomException, IOException {
        float startPrice= 1000;
        float endPrice=70000;

        var commoditiesId = CommodityManager.getInstance().getCommoditiesByPrice(startPrice, endPrice);
        var commodities = CommodityManager.getInstance().getElementsById(commoditiesId);
        responseBody = Jsoup.connect(Constant.Server.BASE + "/commodities/" + "search/" + startPrice + "/" + endPrice).execute().parse();
        Element table = responseBody.select("table").get(0);
        Elements rows = table.select("tr");
        for (int i=1;i<rows.size();i++) {
            String commodityId = rows.get(i).select("td").get(0).html();
            var commodity = CommodityManager.getInstance().getElementById(commodityId);
            assertTrue(startPrice <= commodity.getPrice() && commodity.getPrice() <= endPrice);
        }
    }
    public void searchCommodityByPriceBadFormat() throws IOException {
        String startPrice = "hello"; String endPrice = "goodBye";
        Jsoup.connect(Constant.Server.BASE + "/commodities/" + "search/" + startPrice + "/" + endPrice).execute().parse();
    }
    @Test
    public void testSearchMovieByYearFail(){
        HttpStatusException e = assertThrows(HttpStatusException.class, this::searchCommodityByPriceBadFormat);
        assert403Response(e.getStatusCode());
    }



}
