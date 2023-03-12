package ie.commodity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.JsonHandler;

import java.util.List;
import java.util.Set;

public class CommodityJsonHandler implements JsonHandler<Commodity> {
    private final ObjectMapper mapper;

    public CommodityJsonHandler() {
        mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
    }
    @Override
    public Commodity deserialize(String jsonData) throws JsonProcessingException {
        return mapper.readValue(jsonData, Commodity.class);
    }
    @Override
    public List<Commodity> deserializeList(String jsonData) throws JsonProcessingException {
        return mapper.readValue(jsonData, new TypeReference<List<Commodity>>(){});
    }
    @Override
    public String serialize(Commodity object, Set<String> notIncludedFields) {
        return null;
    }

    @Override
    public String serialize(List<Commodity> objects, Set<String> notIncludedFields) {
        return null;
    }
}
