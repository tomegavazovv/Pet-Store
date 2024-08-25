package com.example.petstore.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class JsonFileUtils {
    private final ResourceLoader resourceLoader;

    public JsonFileUtils(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public <T> List<T> readJsonFile(String filePath, Class<T> type) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:/" + filePath);
        InputStream inputStream = resource.getInputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(inputStream, objectMapper.getTypeFactory().constructCollectionType(List.class, type));
    }
}
