package ie.commodity;

import ie.Router;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

public class CommodityRouter extends Router {
    private CommodityController controller;

    public CommodityRouter() {
        this.controller = new CommodityController();
    }

    @Override
    public void addRoutes(Javalin javalin) {
        javalin.routes(() -> {
            path("/commodities", () -> {
                get(controller::commoditiesHandler);
            });
        });
    }
}


