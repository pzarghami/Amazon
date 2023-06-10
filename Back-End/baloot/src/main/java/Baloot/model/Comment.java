package Baloot.model;

import Baloot.Exeption.CustomException;
import Baloot.model.DTO.CommentDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Comment {
    private String id;
    private User commentOwner;
    private String text;
    private LocalDate createDate;
    private int likeNumber;
    private int dislikeNumber;
    private Commodity commentCommodity;
    private HashMap<String, Short> userVoteMap;

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
    public Comment (String id, String text, String createdDate, HashMap<String, Short> userVoteMap, User commentOwner, Commodity commodity) {
        this.createDate = LocalDate.parse(createdDate);
        this.id = id;
        this.userVoteMap = userVoteMap;
        this.commentOwner=commentOwner;
        this.commentCommodity=commodity;
        this.text = text;
        setLikesAndDislikes();
    }
    private void setLikesAndDislikes(){
        int likes = 0;
        int dislikes = 0;
        for(var value : userVoteMap.values()){
            if(value == 1)
                likes++;
            else if(value == -1)
                dislikes++;
        }
        this.dislikeNumber = dislikes;
        this.likeNumber = likes;
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

    public void voteComment(String user, Integer vote) {
        userVoteMap.put(user, vote.shortValue());
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
        DTO.setCommentCommodityId(Integer.parseInt(commentCommodity.getId()));
        return DTO;
    }
    public Map<String, String> getDBTuple() throws SQLException {
        Map<String, String> tuple = new HashMap<>();
        tuple.put("text", this.text);
        tuple.put("userId", this.commentOwner.getUsername());
        tuple.put("commodityId", this.commentCommodity.getId());
        tuple.put("createDate", this.createDate.toString());
        return tuple;
    }

    public void updateCommentVotes(String username, Integer vote) throws CustomException {
        if (!(-1 <= vote && vote <= 1))
            throw new CustomException("InvalidVoteValueException");
        if(userVoteMap.containsKey(username)) {
            var prevVote = userVoteMap.get(username);
            if (prevVote > 0)
                this.likeNumber -= prevVote;
            else
                this.dislikeNumber += prevVote;
        }
        if (vote > 0)
            this.likeNumber += vote;
        else
            this.dislikeNumber -= vote;
        userVoteMap.put(username, vote.shortValue());

    }
}
