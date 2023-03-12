package ie.comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.Baloot;
import ie.Constant;
import ie.CustomException;
import ie.commodity.Commodity;

import java.util.HashMap;

public class CommentManager {
    ObjectMapper mapper;
    private Integer lastCommentId;
    private HashMap<String, Comment> commentMap;
    private final Baloot database;

    public CommentManager(Baloot database) {
        this.database = database;
        mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
        this.commentMap = new HashMap<>();
        lastCommentId = 0;
    }

    public String addComment(String jsonData) throws JsonProcessingException, CustomException {
        String userEmail = mapper.readTree(jsonData).get("userEmail").asText();
        int commodityId = mapper.readTree(jsonData).get("commodityId").asInt();
        //if(!database.isCommodityExits(commodityId))
           // throw new CustomException(Constant.CMD_NOT_FOUND);
       // if(!database.isUserEmailExists(userEmail))
           // throw new CustomException(Constant.PROVIDER_NOT_FOUND);
        var newComment = mapper.readValue(jsonData, Comment.class);
        commentMap.put(newComment.getId(),newComment);

        return Constant.COMMENT_ADD;
    }
}