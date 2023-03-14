package ie.user;

import ie.Controller;
import ie.CustomException;
import ie.commodity.CommodityManager;
import io.javalin.http.Context;

import java.io.IOException;

public class UserController extends Controller{
    private UserView viewHandler;
    public UserController(){this.viewHandler = new UserView();}

    public void getUserBuyList(Context ctx )throws CustomException, IOException{
        var userId = ctx.pathParam("{user_id}");
        var user = UserManager.getInstance().getElementById(userId);
        var userBuyList = CommodityManager.getInstance().getElementsById(user.getUserBuyList());
        var userParchasedList = CommodityManager.getInstance().getElementsById(user.getUserPurchasedList());
        ctx.html(viewHandler.getUserHtmlResponse(user,userBuyList,userParchasedList));
    }
    public void payment(Context ctx)throws CustomException,IOException{
        var userId = ctx.pathParam("{user_id}");
        var user = UserManager.getInstance().getElementById(userId);
        if(user.buy())
            ctx.html(viewHandler.getSuccessHtmlResponse());
        else
            ctx.html(viewHandler.getErrorHtmlResponse());
    }

}
