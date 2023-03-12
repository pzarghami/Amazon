package ie.commodity;

import ie.Router;
import io.javalin.Javalin;

public class CommodityRouter extends Router {
    private CommodityController controller;
    public CommodityRouter(){this.controller = new CommodityController();}
    @Override
    public void addRoutes(Javalin javalin){
        //todo
    }

}
