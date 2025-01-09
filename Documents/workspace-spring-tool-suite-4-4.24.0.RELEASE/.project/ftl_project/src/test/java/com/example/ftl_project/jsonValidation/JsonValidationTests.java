package com.example.ftl_project.jsonValidation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JsonValidationTests {
	
	@Test
	void testCreateCurrencyProperty() {
		
		ObjectMapper mapper=new ObjectMapper();
		
		ObjectNode resultNode=JsonWithRegex.createCurrencyProperty(mapper);
		String result=resultNode.toString();
		String expectedOutput="{\"type\":\"string\",\"minLength\":2,\"maxLength\":3,\"pattern\":\"^[A-Z]+$\",\"message\":{\"minLength\":\"Minimum Length is 2.\",\"maxLength\":\"Maximum Length is 3(mandatory).\",\"pattern\":\"The currency must be uppercase alphabetic characters only.\"}}";
		assertEquals(expectedOutput,result);
	}

	@Test
	void testInjectReusableSchema() {
		
		ObjectMapper mapper=new ObjectMapper();
		ObjectNode schemaNode=mapper.createObjectNode();
		ObjectNode currencyProperty=JsonWithRegex.createCurrencyProperty(mapper);
		
		schemaNode.putObject("currency").put("$ref", "currencyProperty");
		JsonWithRegex.injectReusableSchema(schemaNode,currencyProperty,mapper);
				
		String expectedOutput = "{\"currency\":{\"type\":\"string\",\"minLength\":2,\"maxLength\":3,\"pattern\":\"^[A-Z]+$\",\"message\":{\"minLength\":\"Minimum Length is 2.\",\"maxLength\":\"Maximum Length is 3(mandatory).\",\"pattern\":\"The currency must be uppercase alphabetic characters only.\"}}}";	
		assertEquals(expectedOutput,schemaNode.toString());
	}
	
	@Test
	void testValidateJsonAgainstSchema() throws IOException {
		
		  String jsonFilePath = "D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\Json Validation\\inputFORcreate.json";
	      String schemaFilePath = "D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\Json Validation\\schema_create.json";

	      ObjectMapper mapper=new ObjectMapper();		
          JsonNode schemaNode = mapper.readTree(new File(schemaFilePath));
          
	      ObjectNode currencyProperty=JsonWithRegex.createCurrencyProperty(mapper);
	      JsonWithRegex.injectReusableSchema(schemaNode,currencyProperty,mapper);

	      ByteArrayOutputStream outContent=new ByteArrayOutputStream();
	      System.setOut(new PrintStream(outContent));
	      
	      JsonWithRegex.validateJsonAgainstSchema(jsonFilePath, schemaNode);

	      String output = outContent.toString();

	      if(output.contains("Valid JSON!")) {
	    	  assertTrue(output.contains("Valid JSON!"),"Json is valid");
	      }else {
	    	  assertTrue(output.contains("Invalid JSON!"),"Json is invalid");
		      if (output.contains("Additional Properties")) {
	            assertTrue(output.contains("Additional Properties"), "Error exist on additional properties");
	        } if(output.contains("Other Validation Errors")) {
	            assertTrue(output.contains("Other Validation Errors"), "Error exist with additional properties");
	        }
	      }
	    }
	
}
