package com.example.ftl_project.jsonValidation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonWithRegex {

    public static void main(String[] args) {
        String jsonFilePath = "D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\Json Validation\\inputFORcreate.json";
        String schemaFilePath = "D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\Json Validation\\schema_create.json";

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode schemaNode = mapper.readTree(new File(schemaFilePath));

            ObjectNode currencyProperty = createCurrencyProperty(mapper);

            injectReusableSchema(schemaNode, currencyProperty, mapper);

            validateJsonAgainstSchema(jsonFilePath, schemaNode);

        } catch (IOException e) {
            System.out.println("Error reading files: " + e.getMessage());
        }
    }

    static ObjectNode createCurrencyProperty(ObjectMapper mapper) {
        ObjectNode currencyProperty = mapper.createObjectNode();
        currencyProperty.put("type", "string");
        currencyProperty.put("minLength", 2);
        currencyProperty.put("maxLength", 3);
        currencyProperty.put("pattern", "^[A-Z]+$");
        

        ObjectNode messageNode = mapper.createObjectNode();
        messageNode.put("minLength", "Minimum Length is 2.");
        messageNode.put("maxLength", "Maximum Length is 3(mandatory).");
        messageNode.put("pattern", "The currency must be uppercase alphabetic characters only.");

        currencyProperty.set("message", messageNode);        
        return currencyProperty;
    }

    static void injectReusableSchema(JsonNode schemaNode, ObjectNode currencyProperty, ObjectMapper mapper) {
        if (schemaNode.isObject()) {
            ObjectNode schemaObject = (ObjectNode) schemaNode;
            Iterator<String> fieldNames = schemaObject.fieldNames();

            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                JsonNode childNode = schemaObject.get(fieldName);

                if (childNode.isObject() && childNode.has("$ref") && childNode.get("$ref").asText().equals("currencyProperty")) {
                    schemaObject.set(fieldName, currencyProperty);
                } else {
                    injectReusableSchema(childNode, currencyProperty, mapper);
                }
            }
        } 
        else if (schemaNode.isArray()) {
            for (JsonNode childNode : schemaNode) {
                injectReusableSchema(childNode, currencyProperty, mapper);
            }
        }
    }

     static void validateJsonAgainstSchema(String jsonFilePath, JsonNode schemaNode) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(new File(jsonFilePath));

        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
        JsonSchema schema = factory.getSchema(schemaNode);

        Set<ValidationMessage> errors = schema.validate(jsonNode);

        if (errors.isEmpty()) {
            System.out.println("Valid JSON!");
        } else {
            System.out.println("Invalid JSON!");

            List<String> additionalProperties = new ArrayList<>();
            List<String> otherErrors = new ArrayList<>();

            String regex = "\\$.*?\\.(\\w+): is not defined in the schema";

            Pattern pattern = Pattern.compile(regex);

            for (ValidationMessage error : errors) {
                String errorMessage = error.getMessage();

                Matcher matcher = pattern.matcher(errorMessage);
                if (matcher.find()) {
                    String propertyName = matcher.group(1);
                    additionalProperties.add(propertyName);
                } else {
                    otherErrors.add(errorMessage);
                }
            }

            if (!additionalProperties.isEmpty()) {
                System.out.println("\nInvalid Additional Properties you entered:");
                System.out.println(additionalProperties);
            }

            if (!otherErrors.isEmpty()) {
                System.out.println("\nOther Validation Errors:");
                for (String error : otherErrors) {
                    System.out.println(error);
                }
            }
        }
    }
}
