package ie.comment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import ie.exeption.CustomException;

import java.util.HashMap;

public class Comment {
    private final String id;
    private String commentEmailOwner;
    private String text;
    private Integer commentLikes;
    private Integer commentDislikes;
    private int commodityId;
    private String date;
    private HashMap<String, Integer> userVoteMap;
    public static Integer lastId = 0;

    @JsonCreator
    private Comment() {
        this.id = String.valueOf(++lastId);
        this.userVoteMap = new HashMap<>();
        this.commentLikes = 0;
        this.commentDislikes = 0;
    }

    @JsonProperty(value = "userEmail", required = true)
    private void setEmailUser(String email) {
        this.commentEmailOwner = email;
    }

    @JsonProperty(value = "commodityId", required = true)
    private void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    @JsonProperty(value = "text", required = true)
    private void setText(String txt) {
        this.text = txt;
    }

    @JsonProperty(value = "date", required = true)
    private void setDate(String date) {
        this.date = date;
    }

    @JsonGetter(value = "userEmail")
    String getUserEmail() {
        return this.commentEmailOwner;
    }

    @JsonGetter(value = "commodityId")
    public int getCommodityId() {
        return this.commodityId;
    }

    @JsonGetter(value = "text")
    private String getText() {
        return this.text;
    }

    @JsonGetter(value = "date")
    private String getDate() {
        return this.date;
    }
    @JsonGetter(value = "id")
    private String getIds() {
        return this.id;
    }
    @JsonGetter(value = "likes")
    private int getLike() {
        return this.commentLikes;
    }
    @JsonGetter(value = "disLikes")
    private int getDisLike() {
        return this.commentDislikes;
    }
    String getId() {
        return this.id;
    }
    private void updateVote(){
        this.commentLikes=0;
        this.commentDislikes=0;
        for(int vote: userVoteMap.values()){
            if(vote==1)
                this.commentLikes+=vote;
            else if(vote==-1)
                this.commentDislikes-=vote;
        }
    }
    public void voteComment(String userId,int vote) throws CustomException {
        userVoteMap.put(userId, vote);
        updateVote();
    }



}
