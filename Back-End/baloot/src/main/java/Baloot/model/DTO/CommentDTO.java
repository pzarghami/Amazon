package Baloot.model.DTO;

import Baloot.model.Commodity;
import Baloot.model.User;
import com.fasterxml.jackson.annotation.JsonAnyGetter;

import javax.xml.stream.events.Comment;

public class CommentDTO {
    private Integer id;
    private  String commentOwner;
    private  String text;
    private  Integer likeNumber;
    private Integer dislikeNumber;
    private  Integer createDate;
    private   Integer commodityOfComment;

    public Integer getId(){return id;}
    public String getCommentOwner(){return commentOwner;}
    public String getText(){return text;}
    public Integer getLikeNumber(){return likeNumber;}
    public Integer getDislikeNumber(){return dislikeNumber;}
    public Integer getCreateDate(){return createDate;}
    public Integer getCommodityOfComment(){return commodityOfComment;}

    public void setId(int id){this.id = id;}
    public void setCommentOwner(String commentOwner){this.commentOwner = commentOwner;}
    public void setText(String text){this.text = text;}
    public void setLikeNumber(int likeNumber){this.likeNumber = likeNumber;}
    public void setDislikeNumber(int dislikeNumber){this.dislikeNumber = dislikeNumber;}
    public void setCreateDate(int createDate){this.createDate = createDate;}
    public void setCommodityOfComment(Integer commodityOfComment){this.commodityOfComment = commodityOfComment;}
}
