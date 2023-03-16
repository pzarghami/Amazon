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
                path("{commodity_id}", () -> {
                    get(controller::commodityHandler);
                });
                path("search", () -> {
                    path("{start_price}/{end_price}", () -> {
                        get(controller::commoditiesHandlerWithFilter);
                    });
                    path ("{categories}", () -> {
                        get(controller::commoditiesHandlerWithFilter);
                    });
                });

            });
            path("rateCommodity", () -> {
                post(controller::rateMovieFormHandler);
                path("{username}/{commodityId}/{rate}", () -> {
                    get(controller::rateMovieFormHandler);
                });
            });
        });
    }
}


