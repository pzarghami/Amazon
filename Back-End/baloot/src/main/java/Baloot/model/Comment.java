package Baloot.model;

import Baloot.Exeption.CustomException;
import Baloot.model.DTO.CommentDTO;

import java.time.LocalDate;
import java.util.HashMap;

public class Comment {
    private String id;
    private User commentOwner;
    private String text;
    private LocalDate createDate;
    private int likeNumber;
    private int dislikeNumber;
    private Commodity commentCommodity;
    private HashMap<User, Integer> userVoteMap;

    public Comment(User commentOwner, String text, Commodity commentCommodity) {
        this.id = null;
        this.commentOwner = commentOwner;
        this.commentCommodity = commentCommodity;
        this.text = text;
        this.createDate = LocalDate.now();
        this.likeNumber = 0;
        this.dislikeNumber = 0;
        this.userVoteMap = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public boolean setId(Integer id) {
        if (this.id == null) {
            this.id = id.toString();
            return true;
        }
        return false;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public int getDislikeNumber() {
        return dislikeNumber;
    }

    private void updateVote() {
        this.likeNumber = 0;
        this.dislikeNumber = 0;
        for (int vote : userVoteMap.values()) {
            if (vote == 1)
                this.likeNumber += vote;
            else if (vote == -1)
                this.dislikeNumber -= vote;
        }
    }

    public void voteComment(User user, int vote) throws CustomException {
        userVoteMap.put(user, vote);
        updateVote();
    }

    public CommentDTO getDTO() {
        var DTO = new CommentDTO();
        DTO.setId(id);
        DTO.setCommentOwner(commentOwner.getUsername());
        DTO.setText(text);
        DTO.setCreateDate(createDate);
        DTO.setLikeNumber(likeNumber);
        DTO.setDislikeNumber(dislikeNumber);
        DTO.setCommentMovieId(Integer.parseInt(commentCommodity.getId()));
        return DTO;
    }
}
