package ie;

import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;

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
}