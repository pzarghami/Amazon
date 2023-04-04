package ie;

import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract class View {
    public static String getCSVFromList(String list) {
        return list.substring(1, list.length()-1).trim().replace("\"", "");
    }
    public String getSuccessHtmlResponse() throws IOException {
        return Jsoup.parse(new File(Constant.HtmlAddress.SUCCES_MSG_ADDR), "UTF-8").html();
    }
    public String getErrorHtmlResponse() throws IOException {
        return Jsoup.parse(new File(Constant.HtmlAddress.ERR_MSG_ADDR), "UTF-8").html();
    }
    public static String getHtmlList(Map<String, String> dataMap) {
        var html = "";
        if (dataMap == null)
            return null;
        html += "<div><ul>";
        for(var data : dataMap.entrySet()) {
            html += "<li>" + data.getKey() + ": " + data.getValue() + "</li";
        }
        html +="</ul><div>";
        return html;
    }
    public static String getCSVFromList(List<String> list) {
        var strList = list.toString();
        return strList.substring(1, strList.length()-1).trim();
    }
}

