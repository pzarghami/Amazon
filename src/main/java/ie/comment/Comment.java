package ie.comment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import ie.exeption.CustomException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class Comment {
    private final String id;
    private String commentUsernameOwner;
    private String text;
    private Integer commentLikes;
    private Integer commentDislikes;
    private String commodityId;
    private String date;
    private HashMap<String, Integer> userVoteMap;
    public static Integer lastId = 0;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    @JsonCreator
    public Comment(String commodityId, String username, String text) {
        this.id = String.valueOf(++lastId);
        this.userVoteMap = new HashMap<>();
        this.commentLikes = 0;
        this.commentDislikes = 0;
        commentUsernameOwner=username;
        this.commodityId=commodityId;
        this.text=text;
        this.date= dtf.format(LocalDateTime.now());
    }

    @JsonProperty(value = "userEmail", required = true)
    private void setEmailUser(String email) {
        this.commentUsernameOwner = email;
    }

    @JsonProperty(value = "commodityId", required = true)
    private void setCommodityId(String commodityId) {
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
    public String getCommentUsernameOwner() {
        return this.commentUsernameOwner;
    }

    @JsonGetter(value = "commodityId")
    public String getCommodityId() {
        return this.commodityId;
    }

    @JsonGetter(value = "text")
    public String getText() {
        return this.text;
    }

    @JsonGetter(value = "date")
    public String getDate() {
        return this.date;
    }
    @JsonGetter(value = "id")
    public String getIds() {
        return this.id;
    }
    @JsonGetter(value = "likes")
    public int getLike() {
        return this.commentLikes;
    }
    @JsonGetter(value = "disLikes")
    public int getDisLike() {
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
