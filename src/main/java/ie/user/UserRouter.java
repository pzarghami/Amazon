package ie.user;

import ie.Router;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

public class UserRouter extends Router {
    private UserController controller;

    public UserRouter() {
        this.controller = new UserController();
    }

    @Override
    public void addRoutes(Javalin javalin) {
        javalin.routes(() -> {
            path("/users", () -> {
                path("{user_id}", () -> {
                    get(controller::getUserBuyList);
                    path("payment", () -> {
                        post(controller::payment);
                    });
                });
            });
        });

    }

}
