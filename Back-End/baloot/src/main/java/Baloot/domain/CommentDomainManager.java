package Baloot.domain;

import Baloot.Exeption.CustomException;
import Baloot.model.Comment;
import Baloot.model.DTO.CommentDTO;
import Baloot.repository.CommentRepo;
import Baloot.repository.CommodityRepo;
import Baloot.repository.UserRepo;

import java.sql.SQLException;

public class CommentDomainManager {
    private static CommentDomainManager instance;

    public static CommentDomainManager getInstance() {
        if (instance == null) {
            instance = new CommentDomainManager();
        }
        return instance;
    }

    public CommentDTO postNewComment(CommentDTO commentDTO) throws CustomException, SQLException {
        var commentCommodity = CommodityRepo.getInstance().getElementById(commentDTO.getCommentCommodityId());
        var newComment = new Comment(UserRepo.loggedInUser, commentDTO.getText(), commentCommodity);
        CommentRepo.getInstance().addElement(newComment);
        commentCommodity.addComment(newComment);
        return newComment.getDTO();
    }

    public CommentDTO voteComment(String commentId, int vote) throws CustomException, SQLException {
        var comment = CommentRepo.getInstance().getElementById(Integer.valueOf(commentId));
        comment.voteComment(UserRepo.loggedInUser.getUsername(), vote);
        return comment.getDTO();
    }

}
