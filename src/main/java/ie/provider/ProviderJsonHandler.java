package ie.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.JsonHandler;

import java.util.List;
import java.util.Set;

public class ProviderJsonHandler implements JsonHandler<Provider> {
    private final ObjectMapper mapper;

    public ProviderJsonHandler() {
        mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
    }
    @Override
    public Provider deserialize(String jsonData) throws JsonProcessingException {
        return mapper.readValue(jsonData, Provider.class);
    }
    @Override
    public List<Provider> deserializeList(String jsonData) throws JsonProcessingException {
        return mapper.readValue(jsonData, new TypeReference<List<Provider>>(){});
    }
    @Override
    public String serialize(Provider object, Set<String> notIncludedFields) {
        return null;
    }

    @Override
    public String serialize(List<Provider> objects, Set<String> notIncludedFields) {
        return null;
    }
}
