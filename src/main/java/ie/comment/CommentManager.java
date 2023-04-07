package ie.comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.*;
import ie.commodity.CommodityManager;
import ie.exeption.CustomException;


import java.util.ArrayList;

public class CommentManager extends Manager<Comment> {
    ObjectMapper mapper;
    private final JsonHandler<Comment> jsonMapper;
    private static CommentManager instance;
    public CommentManager() {

        mapper = new ObjectMapper();
        jsonMapper = new CommentJsonHandler();
        mapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
    }
    public static CommentManager getInstance(){
        if(instance == null)
            instance=new CommentManager();
        return instance;
    }
    @Override
    public String addElement(Comment newObject) throws CustomException {
        var objectId = newObject.getId();
        var commodityId=newObject.getCommodityId();
        if(!CommodityManager.getInstance().isIdValid(String.valueOf(commodityId)))
            throw new CustomException(Constant.CMD_NOT_FOUND);
        if (isIdValid(objectId)) {
            throw new CustomException("ObjectAlreadyExists");
        }
        var commodity=CommodityManager.getInstance().getElementById(String.valueOf(commodityId));
        commodity.addComment(objectId);
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
}