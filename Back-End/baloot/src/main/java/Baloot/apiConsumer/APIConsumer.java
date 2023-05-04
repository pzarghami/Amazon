package Baloot.apiConsumer;

import Baloot.Exeption.CustomException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;

import java.io.IOException;

public abstract class APIConsumer {
    protected String apiUrl;

    public void importData() throws IOException {
        var jsonData = getJsonData();
        var nodeArray = getJsonNode(jsonData);
        loadRepo(nodeArray);
    }

    private String getJsonData() throws IOException {
        return Jsoup.connect(apiUrl).ignoreContentType(true).execute().body();
    }

    private JsonNode getJsonNode(String jsonData) throws JsonProcessingException {
        return new ObjectMapper().readTree(jsonData);
    }

    protected abstract void loadRepo(JsonNode nodeArray);
}

