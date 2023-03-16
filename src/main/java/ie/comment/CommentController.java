package ie.comment;

import ie.Controller;
import ie.CustomException;
import ie.commodity.CommodityManager;

import java.io.IOException;

public class CommentController extends Controller{
    private CommentView viewHandler;
    public CommentController(){this.viewHandler = new CommentView();}
    public void voteCommentHandler(io.javalin.http.Context ctx) throws CustomException, IOException{
        var voteCommentPath=ctx.path();
        ctx.html(voteCommentPath);
        String commentId;
        String userId;
        Integer vote;
        if(voteCommentPath.equals("/voteComment")){

            commentId = ctx.formParamAsClass("comment_id", Integer.class).get().toString();
            userId = ctx.formParam("user_id");
            vote = ctx.formParamAsClass("vote", Integer.class).get();
        }
        else {
            commentId = ctx.pathParamAsClass("commentId", Integer.class).get().toString();
            userId = ctx.pathParam("username");
            vote = ctx.pathParamAsClass("vote", Integer.class).get();
        }
        CommentManager.getInstance().addVote(commentId,userId,vote);
        ctx.html(viewHandler.getSuccessHtmlResponse());
    }
}
