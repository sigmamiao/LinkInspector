package com.sigma.linkinspector.handler;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sigma.linkinspector.util.FileUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Created with IDEA
 * User: Omega
 * Date: 2017/1/25
 * Time: 16:46
 */
public class ConfigHandler {

    public static Map<String, String> fetchConfigInfo() {
        String configPath = FileUtils.getConfigFilePath("config.json");

        String content = FileUtils.readFile(configPath);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        JsonNode root;
        try {
            root = objectMapper.readTree(content);
            return objectMapper.convertValue(root, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }
}
