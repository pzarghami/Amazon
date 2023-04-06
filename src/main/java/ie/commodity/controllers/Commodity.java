package ie.commodity.controllers;

import ie.Constant;
import ie.Controller;
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
            switch (action) {
                case Constant.ActionType.SEARCH_BY_NAME:
                    CommodityManager.getInstance().addFilters(Constant.ActionType.SEARCH_BY_NAME, request.getParameter("search"));
                    break;
                case Constant.ActionType.SEARCH_BY_CAT:
                    CommodityManager.getInstance().addFilters(Constant.ActionType.SEARCH_BY_CAT,request.getParameter("search"));
                    break;
                case Constant.ActionType.CLEAR:
                    CommodityManager.getInstance().clearFilters();
                    break;
                case Constant.ActionType.SORT_BY_PRICE:
                    CommodityManager.getInstance().activeSortingByPrice();
                    break;
                case Constant.ActionType.CLEAR_SORT:
                    CommodityManager.getInstance().sortByPriceFlag=false;
                    break;
                default:
                    sendBadRequestResponse(request, response, Map.ofEntries(entry("action", "Action is not proper")));
                    break;
            }
            response.sendRedirect(Constant.URLS.COMMODITIES);
        }
        catch (CustomException e){

        }
    }

}
