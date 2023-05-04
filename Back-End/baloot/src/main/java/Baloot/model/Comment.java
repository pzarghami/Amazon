package Baloot.model;

import Baloot.Exeption.CustomException;

import java.util.HashMap;

public class Comment {
    private int id;
    private  User commentOwner;
    private  String text;
    private  String createDate;
    private  int likeNumber;
    private int dislikeNumber;
    private HashMap<User, Integer> userVoteMap;

    public Comment(int id, User commentOwner, String text, String createDate){
        this.id = id;
        this.commentOwner = commentOwner;
        this.text = text;
        this.createDate = createDate;
        this.likeNumber = 0;
        this.dislikeNumber = 0;
    }
    public int getId(){return id;}
    public int getLikeNumber(){return likeNumber;}
    public int getDislikeNumber(){return dislikeNumber;}

    private void updateVote(){
        this.likeNumber=0;
        this.dislikeNumber=0;
        for(int vote: userVoteMap.values()){
            if(vote==1)
                this.likeNumber+=vote;
            else if(vote==-1)
                this.dislikeNumber-=vote;
        }
    }
    public void voteComment(User user,int vote) throws CustomException {
        userVoteMap.put(user, vote);
        updateVote();
    }
}
