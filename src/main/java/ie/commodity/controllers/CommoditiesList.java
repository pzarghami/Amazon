package ie.commodity.controllers;

import ie.Baloot;
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
        name = "CommoditiesListPage",
        description = "Display commodities with filters that we choose.",
        urlPatterns = Constant.URLS.COMMODITIES
)
public class CommoditiesList extends Controller {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        if(!Baloot.isLoggedIn())
            response.sendRedirect(Constant.URLS.LOGIN);
        else
            request.getRequestDispatcher(Constant.JSP.COMMODITIES).forward(request, response);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        var action = request.getParameter("action");
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
