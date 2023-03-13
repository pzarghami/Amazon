package ie.commodity;

import ie.Controller;
import javax.naming.Context;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommodityController extends Controller{
    private CommodityView viewHandler;
    public CommodityController(){this.viewHandler = new CommodityView();}
    public void commoditiesHandler(io.javalin.http.Context ctx){
        ctx.html("HI");
    }


}
