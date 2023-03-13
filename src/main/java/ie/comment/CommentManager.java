package ie.comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.*;
import ie.commodity.Commodity;
import ie.commodity.CommodityManager;
import ie.user.User;
import ie.user.UserJsonHandler;
import ie.user.UserManager;

import java.util.ArrayList;
import java.util.HashMap;

public class CommentManager extends Manager<Comment> {
    ObjectMapper mapper;
    private Integer lastCommentId;
    private HashMap<String, Comment> commentMap;
    private final JsonHandler<Comment> jsonMapper;
    private static CommentManager instance;
    public CommentManager() {

        mapper = new ObjectMapper();
        jsonMapper = new CommentJsonHandler();
        mapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
        this.commentMap = new HashMap<>();
        lastCommentId = 0;
    }
    public static CommentManager getInstance(){
        if(instance == null)
            instance=new CommentManager();
        return instance;
    }
    @Override
    public String addElement(Comment newObject) throws CustomException {
        var objectId = newObject.getId();
        if (isIdValid(objectId)) {
            throw new CustomException("ObjectAlreadyExists");
        }
        this.objectMap.put(objectId, newObject);
        return objectId;
    }

    @Override
    public String updateElement(Comment newObject) throws CustomException {
        return null;
    }

    public ArrayList<String> addElementsJson(String jsonData) throws JsonProcessingException, CustomException {
        var objectIds = new ArrayList<String>();
        for (var deserializedObject : jsonMapper.deserializeList(jsonData)) {
            objectIds.add(addElement(deserializedObject));
        }
        return objectIds;
    }

    public String addComment(String jsonData) throws JsonProcessingException, CustomException {
        String userEmail = mapper.readTree(jsonData).get("userEmail").asText();
        int commodityId = mapper.readTree(jsonData).get("commodityId").asInt();
        if(!CommodityManager.getInstance().isIdExist(commodityId))
            throw new CustomException(Constant.CMD_NOT_FOUND);
        if(!UserManager.getInstance().isEmailExists(userEmail))
            throw new CustomException(Constant.PROVIDER_NOT_FOUND);
        var newComment = mapper.readValue(jsonData, Comment.class);
        commentMap.put(newComment.getId(),newComment);

        return Constant.COMMENT_ADD;
    }
}