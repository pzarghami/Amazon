package ie.generic;

import java.util.List;
import java.util.Map;

public class HtmlUtility {
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
