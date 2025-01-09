package com.example.ftl_project.Service;


import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Path;


public class OpenApiValidation {public void validateCurrencyAgainstSpec(String openApiSpecPath, String jsonPath) throws Exception {
    SwaggerParseResult parseResult = new OpenAPIV3Parser().readLocation(openApiSpecPath, null, null);
    if (parseResult.getMessages() != null && !parseResult.getMessages().isEmpty()) {
        throw new RuntimeException("OpenAPI Specification Errors: " + parseResult.getMessages());
    }

    String openApiJson = new ObjectMapper().writeValueAsString(parseResult.getOpenAPI());

    String jsonData = Files.readString(Path.of(jsonPath));

    JSONObject rawSchema = new JSONObject(openApiJson);
    Schema schema = SchemaLoader.load(rawSchema);

    try {
        schema.validate(new JSONObject(jsonData));  // Throws exception if invalid
        System.out.println("JSON is valid against the OpenAPI specification.");
    } catch (Exception e) {
        System.err.println("Validation failed: " + e.getMessage());
    }
}

public static void main(String[] args) {
    OpenApiValidation validator = new OpenApiValidation();
    try {
        String openApiSpecPath = "D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\OpenAPI\\create_currency.yml";
        String jsonPath = "D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\input_create.json";
        validator.validateCurrencyAgainstSpec(openApiSpecPath, jsonPath);
    } catch (Exception e) {
        System.err.println("Error during validation: " + e.getMessage());
    }
}}