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


        var commodityId = ctx.formParamAsClass("commodity_idRate", Integer.class).get().toString();
        var userId = ctx.formParam("user_id");

        var rate = ctx.formParamAsClass("quantity", Integer.class).get();
        CommodityManager.getInstance().addRate(commodityId, userId, rate);
        ctx.html(viewHandler.getSuccessHtmlResponse());
    }




}
