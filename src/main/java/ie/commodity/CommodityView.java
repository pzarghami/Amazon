package ie.commodity;

import ie.Constant;
import ie.JsonHandler;
import ie.View;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CommodityView extends View{
    public String getCommoditiesHtmlList(List<Commodity> commodities) throws IOException {
        var template = Jsoup.parse(new File(Constant.HtmlAddress.COMMODITIES_HTML_ADDR), "UTF-8");
        var table = template.select("table").first();
        var index = 0;
        for (var commodity : commodities) {
            var commodityJson = JsonHandler.getNodeOfObject(commodity);
            var commodityHtml = new Element("tr");
            commodityHtml.append("<td>" + commodityJson.get("id").asText() + "</td>");
            commodityHtml.append("<td>" + commodityJson.get("name").asText() + "</td>");
            commodityHtml.append("<td>" + commodityJson.get("providerId").asText() + "</td>");
            commodityHtml.append("<td>" + commodityJson.get("price").asText() + "</td>");
            commodityHtml.append("<td>" + View.getCSVFromList(commodityJson.get("categories").toPrettyString()) + "</td>");
            commodityHtml.append("<td>" + commodityJson.get("rating").asDouble() + "</td>");
            commodityHtml.append("<td>" + commodityJson.get("inStock").asInt() + "</td>");
            commodityHtml.append("<td><a href=\"" + "/commodities" + "/" + commodityJson.get("id").asInt() + "\">Link</a></td>");
            table.append(commodityHtml.html());
        }
        return template.html();
    }

}
