package ie.discount;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.JsonHandler;
import ie.Manager;
import ie.exeption.CustomException;

import java.util.ArrayList;

public class DiscountManager extends Manager<Discount>{

    private final JsonHandler<Discount> jsonMapper;
    private final ObjectMapper mapper;
    private static DiscountManager instance;

    public DiscountManager () {
        jsonMapper = new DiscountJsonHandler();
        mapper = new ObjectMapper();
    }

    public static DiscountManager getInstance(){
        if(instance == null)
            instance = new DiscountManager();
        return instance;
    }

    @Override
    public String addElement(Discount newObject)throws CustomException{
        var objectId = newObject.getDiscountCode();
        if (isIdValid(objectId)) {
            throw new CustomException("ObjectAlreadyExists");
        }
        this.objectMap.put(objectId, newObject);
        return objectId;
    }

    @Override
    public String updateElement(Discount newObject) throws CustomException {
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
