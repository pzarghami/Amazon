package ie.comment;

import ie.Router;
import ie.user.UserController;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

public class CommentRouter extends Router{
    private CommentController controller;

    public CommentRouter(){this.controller = new CommentController();}

    @Override
    public void addRoutes(Javalin javalin){
        javalin.routes(() -> {
            path("/voteComment", () -> {
                post(controller::voteCommentHandler);
                path("{username}/{commentId}/{vote}", () -> {
                    get(controller::voteCommentHandler);
                });
            });
        });

    }
}
