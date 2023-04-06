package ie;

import java.util.Set;

public class Constant {
    public static final String FIRST_RATE_ID="FIRST_RATE_ID";
    public static final String USR_UPDATE= "user updated.";
    public static final String USR_ADD="user added.";
    public static final String ADD_TO_BUYLIST="added to buy list.";
    public static final String RMV_FROM_BUYLIST="removed from buylist.";
    public static final String USR_NOT_FOUND="The username was not found.";
    public static final String DUPLICATE_COMMODITY="already exists in buy list.";
    public static final String CMD_NOT_FOUND="commodity does not exists.";
    public static final String COMMENT_NOT_FOUND="comment does not exists.";
    public static final String PROVIDER_ADD="provider added.";
    public static final String NO_NEW_COMMODITY="commodity already exists";
    public static final String PROVIDER_NOT_FOUND="provider does not exists";
    public static final String COMMODITY_ADD="commodity added.";
    public static final String OUT_OF_RANGE_RATE="rate is out of range.";
    public static final String LACK_OF_COMMODITY="there is no enough commodity.";
    public static final String ADD_RATE="rate added.";
    public static final String COMMENT_ADD="comment added.";
    public class HtmlAddress{
        public static  final String COMMODITIES_HTML_ADDR="src/main/resources/Commodities.html";
        public static  final String COMMODITY_HTML_ADDR="src/main/resources/Commodity.html";
        public static final String SUCCES_MSG_ADDR="src/main/resources/200.html";
        public static final String ERR_MSG_ADDR="src/main/resources/400.html";
    }
    public static class Server {
        public static final String HOST = "localhost";
        public static final int PORT = 8080;
        public static final String BASE = "http://" + HOST + ':' + PORT;
    }
    public static class FETCH_DATA_ADDR{
        public static  final String USER="http://5.253.25.110:5000/api/users";
        public static  final String PROVIDER="http://5.253.25.110:5000/api/providers";
        public static  final String COMMODITIES="http://5.253.25.110:5000/api/commodities";
        public static  final String COMMENTS="http://5.253.25.110:5000/api/comments";
    }

    public static class Testing {
        public static final String UNKNOWN= "UnKnown";
    }
    public static class JSP {
        public static final String LOGIN = "/auth-views/login.jsp";
        public static final String ERROR = "/error-views/error.jsp";
        public static final String _404_ = "/error-views/404.jsp";
        public static final String ACTOR = "/actor-views/actor.jsp";
        public static final String COMMODITIES = "/commodity-views/commodities.jsp";
        public static final String COMMODITY = "/commodity-views/commodity.jsp";
        public static final String W_LIST  = "/user-views/watchList.jsp";
    }
    public static class URLS {
        public static final String ROOT = "/";
        public static final String LOGIN = "/login";
        public static final String LOGOUT = "/logout";
        public static final String COMMODITIES = "/commodities";
        public static final Set<String> NonAuthURLs = Set.of(LOGIN, LOGOUT);
    }
    public static class ActionType {
        public static final String CLEAR = "clear";
        public static final String SEARCH_BY_CAT = "search_by_category";
        public static final String SEARCH_BY_NAME = "search_by_name";
        public static final String SORT_BY_PRICE="sort_by_price";
        public static final String CLEAR_SORT="clear_sort";
        public static final String RATE = "rate";
        public static final String ADD_TO_WL = "add";
        public static final String LIKE = "like";
        public static final String DISLIKE = "dislike";
        public static final String COMMENT = "comment";
        public static final String DELETE = "delete";
    }



}