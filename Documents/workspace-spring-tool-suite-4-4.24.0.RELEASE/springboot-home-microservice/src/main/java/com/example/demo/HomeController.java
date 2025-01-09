package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HomeController {

	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/home")
	public String home() {
		String product = restTemplate.getForObject("http:springboot-product-microservice/products", String.class);
		return product;
	}
}
