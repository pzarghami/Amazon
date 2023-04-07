package ie.user.controllers;

import ie.Baloot;
import ie.Constant;
import ie.Controller;
import ie.commodity.CommodityManager;
import ie.exeption.CustomException;
import ie.exeption.ObjectNotFoundException;
import ie.user.UserManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.ObjectStreamException;

@WebServlet(Constant.URLS.BUYLIST)
public class UserBuyList extends Controller {

    public void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
        if(!Baloot.isLoggedIn())
            response.sendRedirect(Constant.URLS.LOGIN);
        else{
            try{
                var commodityIds = Baloot.loggedInUser.getUserBuyList();
                var commodities = CommodityManager.getInstance().getElementsById(commodityIds);

                request.setAttribute("commodities",commodities);
                request.getRequestDispatcher(Constant.JSP.BUYLIST).forward(request,response);

            }catch (CustomException e){
                e.printStackTrace();
            }
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        var action = request.getParameter("action");
        var commodityId = request.getParameter("commodity_id");
        try{
            if(action.equals("delete")){
                UserManager.getInstance().removeFromBuyList(Baloot.loggedInUser.getUsername(),commodityId);
                response.sendRedirect(Constant.URLS.BUYLIST);
            }

        }catch (CustomException e){
            sendBadRequestResponse(request,response,null);
        }
    }
}
