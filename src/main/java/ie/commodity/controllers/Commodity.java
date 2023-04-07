package ie.commodity.controllers;

import ie.Baloot;
import ie.Constant;
import ie.Controller;
import ie.comment.Comment;
import ie.comment.CommentManager;
import ie.commodity.CommodityManager;
import ie.exeption.CustomException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

import static java.util.Map.entry;

@WebServlet(
        name = "Commodity page",
        description = "Display information of one commodity",
        urlPatterns = Constant.URLS.COMMODITIES+"/*"
)
public class Commodity extends Controller {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        var pathParts = splitPathParams(request.getPathInfo());

        if(pathParts!=null) {
            var commodityId=pathParts[0];
            request.setAttribute("commodityId", commodityId);
            request.getRequestDispatcher(Constant.JSP.COMMODITY).forward(request, response);
        }
        else
            request.getRequestDispatcher(Constant.JSP.COMMODITIES).forward(request, response);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        var action = request.getParameter("action");
        var pathParts = splitPathParams(request.getPathInfo());
        var commodityId=pathParts[0];

        try {
            ie.commodity.Commodity commodity= CommodityManager.getInstance().getElementById(commodityId);
            switch (action) {
                case Constant.ActionType.COMMENT:
                    var commentText = request.getParameter("comment");
                    CommentManager.getInstance().addElement(new Comment(commodityId, Baloot.loggedInUser.getUsername(), commentText));
                    break;
                case Constant.ActionType.RATE:
                    var rate = Integer.parseInt(request.getParameter("quantity"));
                    commodity.addRate(Baloot.loggedInUser.getUsername(), rate);
                    break;
                case Constant.ActionType.ADD_TO_BUY:
                    Baloot.loggedInUser.addToUserBuyList(commodityId);
                    break;
                case Constant.ActionType.LIKE:
                    var comment= CommentManager.getInstance().getElementById(request.getParameter("comment_id"));
                    comment.voteComment(Baloot.loggedInUser.getUsername(),1);
                    break;
                case Constant.ActionType.DISLIKE:
                    var commentDis= CommentManager.getInstance().getElementById(request.getParameter("comment_id"));
                    commentDis.voteComment(Baloot.loggedInUser.getUsername(),-1);
                    break;
                default:
                    sendBadRequestResponse(request, response, Map.ofEntries(entry("action", "Action is not proper")));
                    break;
            }
            response.sendRedirect(Constant.URLS.COMMODITIES+"/" + commodityId);
        }
        catch (CustomException e){

        }
    }

}
