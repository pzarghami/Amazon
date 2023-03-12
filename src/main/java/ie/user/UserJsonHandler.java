package ie.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.JsonHandler;

import java.util.List;
import java.util.Set;

public class UserJsonHandler implements JsonHandler<User> {
    private final ObjectMapper mapper;

    public UserJsonHandler() {
        mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
    }
    @Override
    public User deserialize(String jsonData) throws JsonProcessingException {
        return mapper.readValue(jsonData, User.class);
    }
    @Override
    public List<User> deserializeList(String jsonData) throws JsonProcessingException {
        return mapper.readValue(jsonData, new TypeReference<List<User>>(){});
    }
    @Override
    public String serialize(User object, Set<String> notIncludedFields) {
        return null;
    }

    @Override
    public String serialize(List<User> objects, Set<String> notIncludedFields) {
        return null;
    }
}
