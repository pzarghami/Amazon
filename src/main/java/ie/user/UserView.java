package ie.user;

import ie.commodity.Commodity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import ie.JsonHandler;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ie.View;

public class UserView extends View {
    public String getUserHtmlResponse(User user, List<Commodity> userBuyList, List<Commodity> UserPurchasedList) throws IOException {
        var template = Jsoup.parse(new File("src/main/resources/User.html"), "UTF-8");
        var userJson = JsonHandler.getNodeOfObject(user);

        var listItems = template.select("li");
        listItems.get(0).append(userJson.get("username").asText());
        listItems.get(1).append(userJson.get("email").asText());
        listItems.get(2).append(userJson.get("birthDate").asText());
        listItems.get(3).append(userJson.get("address").asText());
        listItems.get(4).append(userJson.get("credit").asText());
        var paymentFormAction = String.format("/users/%s/payment",userJson.get("username").asText());
        listItems.get(5).getElementById("payment_form").attr("action",paymentFormAction);

        var table = template.select("table").first();
        for (var commodity : userBuyList) {
            var commodityJson = JsonHandler.getNodeOfObject(commodity);
            var commodityHtml = new Element("tr");
            commodityHtml.append("<td>" + commodityJson.get("id").asText());
            commodityHtml.append("<td>" + commodityJson.get("name").asText());
            commodityHtml.append("<td>" + commodityJson.get("providerId").asText());
            commodityHtml.append("<td>" + commodityJson.get("price").asText());
            commodityHtml.append("<td>" + View.getCSVFromList(commodityJson.get("categories").toPrettyString()));
            commodityHtml.append("<td>" + commodityJson.get("rating").asText());
            commodityHtml.append("<td>" + commodityJson.get("inStock").asText());
            commodityHtml.append("<td><a href=\"" + "/commodities/" + commodityJson.get("id").asText() + "\">Link</a></td>");
            var removeFormStr = String.format("""
                    <td>
                        <form action="/removeFromByList/%s/%s" method="POST" >
                                <input id="form_commodity_id" type="hidden" name="commodityId" value="%s">
                                <button type="submit">Remove</button>
                        </form>
                    </td>
                    """,userJson.get("username").asText(),Integer.toString(commodity.getId()) ,Integer.toString(commodity.getId()));
            commodityHtml.append(removeFormStr);
//            commodityHtml.getElementById("form_commodity_id").attr("value",Integer.toString(commodity.getId()));
            table.append(commodityHtml.html());
        }


        return template.html();
    }


}
