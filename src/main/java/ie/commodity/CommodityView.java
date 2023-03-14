package ie.commodity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import ie.Constant;
import ie.CustomException;
import ie.JsonHandler;
import ie.View;
import ie.comment.CommentManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    public String getCommodityHtml(ArrayList<String> comments,Commodity commodity) throws IOException, CustomException {
        var template = Jsoup.parse(new File(Constant.HtmlAddress.COMMODITY_HTML_ADDR), "UTF-8");

        var commodityJson = JsonHandler.getNodeOfObject(commodity);
        //Making the first part which is a list for movie's information
        var listItems = commodityInfo(commodityJson,template);
        String baseUrl = "commodities" + "/" + commodity.getId() + "/";

        // Making the add to buy list form
        var buyListForm = template.select("form").get(1);
        buyListForm.attr("action", "/addToBuyList");
        var commodityInput = buyListForm.getElementById("commodity_id");
        commodityInput.attr("value", commodityJson.get("id").asText());

        //Making rate movie form
        var rateForm = template.select("form").get(0);
        rateForm.attr("action", baseUrl + "rateCommodity");
        var rateInput = buyListForm.getElementById("commodity_id");
        commodityInput.attr("value", commodityJson.get("id").asText());

        //Making comment form
//        var commentTable = template.select("table").first();
//        for (String commentId : comments) {
//            var commentNode = JsonHandler.getNodeOfObject(CommentManager.getInstance().getElementById(commentId));
//            commentTable.append(makeCommentRow(commentNode, CommentRouter.UrlsPath.VCOMMENT));
//        }

        return template.html();

    }
//    private String makeCommentRow(JsonNode commentNode, String actionUrl) throws IOException {
//        var commentHtml = new Element("tr");
//        commentHtml.append(makeTableColumn(commentNode.get("userEmail").asText()));
//        commentHtml.append(makeTableColumn(commentNode.get("text").asText()));
//        commentHtml.append(makeTableColumn(commentNode.get(Constant.Comment.DISLIKES).asText()));
//        commentHtml.append(makeTableColumn(makeVoteCommentForm(actionUrl, commentNode.get(Constant.Comment.ID).asText())));
//
//        return commentHtml.html();
//    }
    private String makeTableColumn(String value) {
        return "<td>" + value + "</td>";
    }
    public Elements commodityInfo(JsonNode commodityJson, Document template){

        List<String> values = Arrays.asList(
                commodityJson.get("id").asText(),
                commodityJson.get("name").asText(),
                commodityJson.get("providerId").asText(),
                commodityJson.get("price").asText(),
                View.getCSVFromList(commodityJson.get("categories").toPrettyString()),
                commodityJson.get("rating").asText(),
                commodityJson.get("inStock").asText()
        );
        var listItems = template.select("li");
        for (int i = 0; i < values.size(); i++) {
            listItems.get(i).append(values.get(i));
        }
        return listItems;
    }


}
