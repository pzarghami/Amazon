package ie.commodity;

import ie.Controller;
import ie.CustomException;
import io.javalin.http.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommodityController extends Controller{
    private CommodityView viewHandler;
    public CommodityController(){this.viewHandler = new CommodityView();}
    public void commoditiesHandler(io.javalin.http.Context ctx) throws CustomException, IOException {
        var commodities = CommodityManager.getInstance().getElementsById(null);
        ctx.html(viewHandler.getCommoditiesHtmlList(commodities));
    }
    public void commodityHandler(io.javalin.http.Context ctx) throws CustomException, IOException {
        var commodityId = ctx.pathParamAsClass("commodity_id", Integer.class).get().toString();
        var commodity=CommodityManager.getInstance().getElementById(commodityId);
        var commodityCommentsList=commodity.getComments();
        ctx.html(viewHandler.getCommodityHtml(commodityCommentsList,commodity));

    }
    public void rateMovieFormHandler(Context ctx) throws CustomException, IOException {
        var ratePath=ctx.path();
        ctx.html(ratePath);
        String commodityId;
        String userId;
        Integer rate;
        if(ratePath.equals("/rateCommodity")){

            commodityId = ctx.formParamAsClass("commodity_idRate", Integer.class).get().toString();
            userId = ctx.formParam("user_id");
            rate = ctx.formParamAsClass("quantity", Integer.class).get();
        }
        else {
            commodityId = ctx.pathParamAsClass("commodityId", Integer.class).get().toString();
            userId = ctx.pathParam("username");
            rate = ctx.pathParamAsClass("rate", Integer.class).get();
        }
        CommodityManager.getInstance().addRate(commodityId, userId, rate);
        ctx.html(viewHandler.getSuccessHtmlResponse());

    }




}
