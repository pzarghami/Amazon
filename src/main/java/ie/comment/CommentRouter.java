package ie.comment;

import ie.Router;
import ie.user.UserController;
import io.javalin.Javalin;

public class CommentRouter extends Router{
    private CommentController controller;

    public CommentRouter(){this.controller = new CommentController();}

    @Override
    public void addRoutes(Javalin javalin){
        //todo
    }
}
