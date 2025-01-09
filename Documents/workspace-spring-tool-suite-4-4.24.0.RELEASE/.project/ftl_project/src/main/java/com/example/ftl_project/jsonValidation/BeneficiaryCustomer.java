package com.example.ftl_project.jsonValidation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BeneficiaryCustomer {

    public static void main(String[] args) {
        String jsonFilePath = "D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\Json Validation\\beneficiary.json";   // JSON file containing your data
        String schemaFilePath = "D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\Json Validation\\beneficiarySchema.json"; // JSON Schema file

        try {
            validateJsonAgainstSchema(jsonFilePath, schemaFilePath);
        } catch (IOException e) {
            System.out.println("Error reading files: " + e.getMessage());
        }
    }

    private static void validateJsonAgainstSchema(String jsonFilePath, String schemaFilePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonNode = mapper.readTree(new File(jsonFilePath));
        JsonNode schemaNode = mapper.readTree(new File(schemaFilePath));

        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
        JsonSchema schema = factory.getSchema(schemaNode);

        Set<ValidationMessage> errors = schema.validate(jsonNode);

        if (errors.isEmpty()) {
            System.out.println("Valid JSON!");
        } else {
            System.out.println("Invalid JSON!");

            Map<String, List<String>> additionalPropertiesMap = new HashMap<>();
            List<String> otherErrors = new ArrayList<>();

            String regex = "\\$.*?\\.(\\w+): is not defined in the schema";
            Pattern pattern = Pattern.compile(regex);

            for (ValidationMessage error : errors) {
                String errorMessage = error.getMessage();

                Matcher matcher = pattern.matcher(errorMessage);
                if (matcher.find()) {
                    String propertyName = matcher.group(1);
                    additionalPropertiesMap.computeIfAbsent("Other Properties", k -> new ArrayList<>()).add(propertyName);
                } else {
                    otherErrors.add(errorMessage);
                }
            }

            if (!additionalPropertiesMap.isEmpty()) {
                System.out.println("\nInvalid Additional Properties:");
                additionalPropertiesMap.forEach((key, values) -> {
                    System.out.println(key + ": " + values);
                });
            }

            if (!otherErrors.isEmpty()) {
                System.out.println("\nOther Validation Errors:");
                otherErrors.forEach(System.out::println);
            }
        }
    }
}
