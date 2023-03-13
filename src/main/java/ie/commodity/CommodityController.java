package ie.commodity;

import ie.Controller;
import ie.CustomException;

import javax.naming.Context;
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


}
