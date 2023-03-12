package ie.user;

import ie.Router;
import io.javalin.Javalin;

public class UserRouter extends Router{
    private UserController controller;

    public UserRouter(){this.controller = new UserController();}

    @Override
    public void addRoutes(Javalin javalin){
        //todo
    }

}
