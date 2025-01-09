package com.example.ftl_project.jsonValidation;

import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class JsonSchemaValidation {

    public static void main(String[] args) {
        String jsonFilePath = "D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\Json Validation\\inputFORcreate.json";   // JSON file containing your data
        String schemaFilePath = "D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\Json Validation\\schema_create.json"; // JSON Schema file

        try {
            boolean isValid = validateJsonAgainstSchema(jsonFilePath, schemaFilePath);
            if (isValid) {
                System.out.println("Valid JSON!");
            } else {
                System.out.println("Invalid JSON!");
            }
        } catch (IOException e) {
            System.out.println("Error reading files: " + e.getMessage());
        }
    }

    private static boolean validateJsonAgainstSchema(String jsonFilePath, String schemaFilePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonNode = mapper.readTree(new File(jsonFilePath));
        JsonNode schemaNode = mapper.readTree(new File(schemaFilePath));

        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
        JsonSchema schema = factory.getSchema(schemaNode);

        Set<ValidationMessage> errors = schema.validate(jsonNode);

        if (errors.isEmpty()) {
            return true;
        } else {
            errors.forEach(error -> System.out.println(error.getMessage()));
            return false;
        }
    }
}
