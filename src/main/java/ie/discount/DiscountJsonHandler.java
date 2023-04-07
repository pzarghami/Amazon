package ie.discount;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.JsonHandler;


import java.util.List;
import java.util.Set;

public class DiscountJsonHandler implements JsonHandler<Discount> {
    private final ObjectMapper mapper;

    public DiscountJsonHandler() {
        mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
    }
    @Override
    public Discount deserialize(String jsonData) throws JsonProcessingException {
        return mapper.readValue(jsonData, Discount.class);
    }
    @Override
    public List<Discount> deserializeList(String jsonData) throws JsonProcessingException {
        return mapper.readValue(jsonData, new TypeReference<List<Discount>>(){});
    }
    @Override
    public String serialize(Discount object, Set<String> notIncludedFields) {
        return null;
    }

    @Override
    public String serialize(List<Discount> objects, Set<String> notIncludedFields) {
        return null;
    }
}
