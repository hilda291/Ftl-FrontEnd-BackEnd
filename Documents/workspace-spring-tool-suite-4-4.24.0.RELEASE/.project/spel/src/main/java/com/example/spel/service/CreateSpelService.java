package com.example.spel.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import com.example.spel.entity.CreateSpelEntity;

public class CreateSpelService {

	 private final ExpressionParser parser = new SpelExpressionParser();

	 CreateSpelEntity inputJson;
	 
	 Map<String,String> validationErrors=new HashMap<>();
	 
	 public String sendersReference(String sendersReference) {
		 String expression=parser.parseExpression("sendersReference matches '^$' ? 'valid':'invalid'")
				 .getValue(inputJson, String.class);
		 return expression;
	 }
}
