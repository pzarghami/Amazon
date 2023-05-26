package Baloot.model.DTO;

import java.time.LocalDate;

public class CommentDTO {
    private String id;
    private String commentOwner;
    private String text;
    private Integer likeNumber;
    private Integer dislikeNumber;
    private LocalDate createDate;
    private Integer commentCommodityId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommentOwner() {
        return commentOwner;
    }

    public void setCommentOwner(String commentOwner) {
        this.commentOwner = commentOwner;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public Integer getDislikeNumber() {
        return dislikeNumber;
    }

    public void setDislikeNumber(int dislikeNumber) {
        this.dislikeNumber = dislikeNumber;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Integer getCommentCommodityId() {
        return commentCommodityId;
    }

    public void setCommentCommodityId(Integer commentMovieId) {
        this.commentCommodityId = commentMovieId;
    }
}
