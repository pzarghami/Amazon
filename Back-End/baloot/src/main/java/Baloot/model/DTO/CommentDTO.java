package Baloot.model.DTO;

import Baloot.model.Commodity;
import Baloot.model.User;

import javax.xml.stream.events.Comment;

public class CommentDTO {
    private int id;
    private  User commentOwner;
    private  String text;
    private  int likeNumber;
    private int dislikeNumber;
    private  int createDate;
    private   Commodity commodityOfComment;

    public int getId(){return id;}
    public User getCommentOwner(){return commentOwner;}
    public String getText(){return text;}
    public int getLikeNumber(){return likeNumber;}
    public int getDislikeNumber(){return dislikeNumber;}
    public int getCreateDate(){return createDate;}
    public Commodity getCommodityOfComment(){return commodityOfComment;}

    public void setId(int id){this.id = id;}
    public void setCommentOwner(User commentOwner){this.commentOwner = commentOwner;}
    public void setText(String text){this.text = text;}
    public void setLikeNumber(int likeNumber){this.likeNumber = likeNumber;}
    public void setDislikeNumber(int dislikeNumber){this.dislikeNumber = dislikeNumber;}
    public void setCreateDate(int createDate){this.createDate = createDate;}
    public void setCommodityOfComment(Commodity commodityOfComment){this.commodityOfComment = commodityOfComment;}
}
