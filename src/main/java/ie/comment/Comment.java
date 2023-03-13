package ie.comment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class Comment {
    private String id;
    private String commentEmailOwner;
    private String text;
    private Integer commentLikes;
    private Integer commentDislikes;
    private int commodityId;
    private String date;
    private HashMap<String, Short> userVoteMap;
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
    private String getUserEmail() {
        return this.commentEmailOwner;
    }

    @JsonGetter(value = "commodityId")
    private int getCommodityId() {
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

    String getId() {
        return this.id;
    }



}
