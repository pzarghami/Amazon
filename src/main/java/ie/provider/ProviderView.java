package ie.provider;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import ie.JsonHandler;
import ie.View;
import ie.commodity.Commodity;

public class ProviderView extends View{
    public String getProviderHtmlResponse(Provider provider, @NotNull List<Commodity>commodities)throws IOException{
        var providerJson = JsonHandler.getNodeOfObject(provider);
        var template = Jsoup.parse(new File("src/main/resources/Provider.html"),"UTF-8");
        var listItems = template.select("li");
        var table = template.select("table");
        listItems.get(0).append(providerJson.get("id").asText());
        listItems.get(1).append(providerJson.get("name").asText());
        listItems.get(2).append(providerJson.get("registryDate").asText());
        listItems.get(3).append(providerJson.get("avrageRate").asText());
        for (var commodity :commodities){
            var commodityJson = JsonHandler.getNodeOfObject(commodity);
            var commodityHtml = new Element("tr");
            commodityHtml.append("<td>" + commodityJson.get("id").asText() + "</td>");
            commodityHtml.append("<td>" + commodityJson.get("name").asText() + "</td>");
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
