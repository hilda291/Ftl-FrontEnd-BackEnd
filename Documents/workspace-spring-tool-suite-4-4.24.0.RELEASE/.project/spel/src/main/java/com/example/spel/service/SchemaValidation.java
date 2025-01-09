package com.example.spel.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SchemaValidation {

    public String validateJson(String jsonInput, String schemaFilePath) throws IOException {
        ObjectMapper map = new ObjectMapper();
        JsonNode jnode = map.readTree(jsonInput);
        JsonNode sNode = map.readTree(new File(schemaFilePath));

        ArrayList<String> additionalProperties = new ArrayList<>();
        ObjectNode entityCodeProperty = createEntityCodeProperty(map);
        insertIntoSchema(sNode, entityCodeProperty);

        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
        JsonSchema schema = factory.getSchema(sNode);

        Set<ValidationMessage> err = schema.validate(jnode);
        StringBuilder response = new StringBuilder();

        if (err.isEmpty()) {
            response.append("Valid Json\n");
        } else {
            err.forEach(error -> {
                String regex = "\\$.*?\\.(\\w+): is not defined in the schema";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(error.getMessage());

                if (matcher.find()) {
                    String additionalProp = matcher.group(1);
                    additionalProperties.add(additionalProp);
                }

                response.append(error.getMessage()).append("\n");
            });
            response.append("Invalid Json\n");
        }

        if (!additionalProperties.isEmpty()) {
            response.append("Additional Properties Found:\n");
            additionalProperties.forEach(response::append);
        }

        return response.toString();
    }

    public ObjectNode createEntityCodeProperty(ObjectMapper mapper) {
        ObjectNode entityCodeProperty = mapper.createObjectNode();
        entityCodeProperty.put("type", "string");
        entityCodeProperty.put("minLength", 3);
        entityCodeProperty.put("pattern", "^(?![Nn][Uu][Ll][Ll]$).*");
        ObjectNode message = mapper.createObjectNode();  
        message.put("minLength", "The 'entityCode' must be at least 3 characters long.");
        message.put("pattern","'entityCode' cannot be 'null' as a string.");
        entityCodeProperty.set("message", message);

        return entityCodeProperty;
    }

    public void insertIntoSchema(JsonNode schemaNode, ObjectNode property) {
        if (schemaNode.isObject()) {
            Iterator<String> field = schemaNode.fieldNames();

            while (field.hasNext()) {
                String fieldName = field.next();
                JsonNode node = schemaNode.get(fieldName);

                if (node.isObject() && node.has("$ref") && node.get("$ref").asText().equals("entityCodeProperty")) {
                    ((ObjectNode) schemaNode).set(fieldName, property);
                } else {
                    insertIntoSchema(node, property);
                }
            }
        } else if (schemaNode.isArray()) {
            for (JsonNode node : schemaNode) {
                insertIntoSchema(node, property);
            }
        }
    }
}