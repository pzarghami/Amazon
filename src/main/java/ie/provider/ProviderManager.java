package ie.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.*;
import ie.exeption.CustomException;

import java.util.ArrayList;
import java.util.HashMap;

public class ProviderManager extends Manager<Provider>{
    private final HashMap<Integer, Provider> providerMap;
    private static ProviderManager instance = null;
    private final ObjectMapper mapper;
    private final JsonHandler<Provider> jsonMapper;



    public ProviderManager () {
        mapper = new ObjectMapper();
        jsonMapper = new ProviderJsonHandler();
        providerMap = new HashMap<>();

    }
    public static ProviderManager getInstance() {
        if (instance == null) {
            instance = new ProviderManager();
        }
        return instance;
    }
    @Override
    public String addElement(Provider newObject) throws CustomException {
        var objectId = Integer.toString(newObject.getId());
        if (isIdValid(objectId)) {
            throw new CustomException("ObjectAlreadyExists");
        }
        this.objectMap.put(objectId, newObject);
        return objectId;
    }

    @Override
    public String updateElement(Provider newObject) throws CustomException {
        return null;
    }

    public ArrayList<String> addElementsJson(String jsonData) throws JsonProcessingException, CustomException {
        var objectIds = new ArrayList<String>();
        for (var deserializedObject : jsonMapper.deserializeList(jsonData)) {
            objectIds.add(addElement(deserializedObject));
        }
        return objectIds;
    }

    public String  updateOrAddProvider(String jsonData) throws  JsonProcessingException {
        int id = mapper.readTree(jsonData).get("id").asInt();
        if(isIDValid(id)){
            updateUser(id,jsonData);
        }else{
            addUser(id,jsonData);
        }
        return Constant.PROVIDER_ADD;
    }

    public void setAverageRate(int providerId,float rate,int commodityId){
        var provider=providerMap.get(providerId);
        provider.setAverageRate(commodityId,rate);
    }
    private void updateUser(int id, String jsonData) throws JsonProcessingException{
        mapper.readerForUpdating(providerMap.get(id)).readValue(jsonData);
    }

    private void addUser(int id,String jsonData) throws JsonProcessingException{
        var newProvider = mapper.readValue(jsonData, Provider.class);
        providerMap.put(id,newProvider);
    }

    public boolean isIDValid(int id){
        return providerMap.containsKey(id);
    }

}
