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

    public void addCredit(Context ctx)throws CustomException,IOException{
        var username = ctx.pathParam("{user_id}");
        var credit = ctx.pathParamAsClass("{credit}", Integer.class).get();
        var user = UserManager.getInstance().getElementById(username);
        user.addCredit(credit);
        ctx.html(viewHandler.getSuccessHtmlResponse());
    }
    public void addToBuyList(Context ctx)throws CustomException,IOException{
        if(ctx.method()=="POST"){
            var username = ctx.formParam("{user_id}");
            System.out.println(username);
            var commodityId = ctx.formParam("{commodity_id}");
            ctx.html(commodityId + "AND " + username);
//            System.out.println(commodityId);
//            UserManager.getInstance().addToBuyList(username,commodityId);
//            ctx.html(viewHandler.getSuccessHtmlResponse());
        }
        else{
            var username = ctx.pathParam("{username}");
            var commodityId = ctx.pathParam("{commodityId}");
            //UserManager.getInstance().addToBuyList(username,commodityId);
            ctx.html(commodityId + "AND " + username);
        }
    }
    public void removeFromBuyList(Context ctx)throws CustomException,IOException{
        var username = ctx.pathParam("{username}");
        var commodityId = ctx.pathParam("{commodityId}");
        UserManager.getInstance().removeFromBuyList(username,commodityId);
        ctx.html(viewHandler.getSuccessHtmlResponse());
    }


}
