package com.example.ftl_project.Controller;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.example.ftl_project.Service.DatabaseService;
import com.example.ftl_project.Service.JsonTOJsonService;
import com.example.ftl_project.entity.Dataforjson;
import com.example.ftl_project.entity.DetailInputJson;
import com.example.ftl_project.entity.ListInputJson;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping("/ftlCheck")
@Component
public class JsonToJsonController {

	private final JsonTOJsonService jjService;

	private final DatabaseService databaseService;
	
	 public JsonToJsonController(JsonTOJsonService jjService, DatabaseService databaseService) {
		super();
		this.jjService = jjService;
		this.databaseService = databaseService;
	}


	Logger logger = Logger.getLogger(getClass().getName());

	public List<String> fetchMasterRefId() {
		return databaseService.fetchMasterRefIdList();
	}

	public String dataForMasterID() {
		List<String> masterRefId = fetchMasterRefId();
		String data = masterRefId.get(0);
		logger.info(data);
		return data;
	}
	

	@PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
	public String createGenerateXml(@RequestBody String jsonContent) throws RestClientException, TemplateException, IOException {
		
		//String filePath = "\\toXml_create.ftl";


			String ftlContent = new String(Files.readAllBytes(Paths.get("D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\toXML_create.ftl")));

			String mockUrl = "http://bsit-srv04:8003/tiplus2-deploy2/hello";
			String xmlOutput = jjService.processTemplate(jsonContent, ftlContent);
			
	            
		//	String xmlOutput = jjService.processTemplate(jsonContent, filePath);

			logger.info(xmlOutput);

			XmlMapper xmlMapper = new XmlMapper();

			JsonNode jsonNode = xmlMapper.readTree(xmlOutput);

			ObjectMapper objectMapper = new ObjectMapper();

			String jsonData = objectMapper.writeValueAsString(jsonNode);

			JsonNode rootNode = objectMapper.readTree(jsonData);

			String theirReference = rootNode.path("TFCPCCRT").path("Context").path("TheirReference").asText();

			String dataMasterID = dataForMasterID();
			logger.info(theirReference);
			
			String ftlFilePath = new String(Files.readAllBytes(Paths.get("D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\toJson_create.ftl")));


			return JsonTOJsonService.processMockApi(mockUrl, xmlOutput, ftlFilePath, theirReference, dataMasterID);		
		
		//	return xmlOutput;
	}
	
	@PostMapping(value = "/list", consumes = "application/json", produces = "application/json")
	public String listGenerateXml(@RequestBody String jsonContent) throws RestClientException, TemplateException, IOException {

			String ftlContent = new String(Files.readAllBytes(Paths.get("D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\toXML_list.ftl")));

			String mockUrl = "http://bsit-srv04:8003/tiplus2-deploy2/hello";
			String xmlOutput = jjService.processTemplate(jsonContent, ftlContent);
			
			logger.info(xmlOutput);

			XmlMapper xmlMapper = new XmlMapper();

			JsonNode jsonNode = xmlMapper.readTree(xmlOutput);

			ObjectMapper objectMapper = new ObjectMapper();

			String jsonData = objectMapper.writeValueAsString(jsonNode);

			JsonNode rootNode = objectMapper.readTree(jsonData);

			String theirReference = rootNode.path("TFCPCCRT").path("Context").path("TheirReference").asText();

			String dataMasterID = dataForMasterID();
			logger.info(theirReference);
			
			String ftlFilePath = new String(Files.readAllBytes(Paths.get("D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\toJson_list.ftl")));

			return JsonTOJsonService.processMockApi(mockUrl, xmlOutput, ftlFilePath, theirReference, dataMasterID);		
		
	}

	@PostMapping(value = "/detail", consumes = "application/json", produces = "application/json")
	public String DetailGenerateXml(@RequestBody String jsonContent) throws RestClientException, TemplateException, IOException {

			String ftlContent = new String(Files.readAllBytes(Paths.get("D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\toXML_detail.ftl")));

			String mockUrl = "http://bsit-srv04:8003/tiplus2-deploy2/hello";
			String xmlOutput = jjService.processTemplate(jsonContent, ftlContent);
			
			logger.info(xmlOutput);

			XmlMapper xmlMapper = new XmlMapper();

			JsonNode jsonNode = xmlMapper.readTree(xmlOutput);

			ObjectMapper objectMapper = new ObjectMapper();

			String jsonData = objectMapper.writeValueAsString(jsonNode);

			JsonNode rootNode = objectMapper.readTree(jsonData);

			String theirReference = rootNode.path("TFCPCCRT").path("Context").path("TheirReference").asText();

			String dataMasterID = dataForMasterID();
			logger.info(theirReference);
			
			String ftlFilePath = new String(Files.readAllBytes(Paths.get("D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\toJson_detail.ftl")));

			return JsonTOJsonService.processMockApi(mockUrl, xmlOutput, ftlFilePath, theirReference, dataMasterID);		
		
	}
	
	//create input form
	public String generateJson(String jsonContent) throws IOException, TemplateException  {
		String ftlContent = new String(Files.readAllBytes(Paths.get("D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\inputJson.ftl")));

		StringWriter writer = new StringWriter();
	      

		Configuration config = new Configuration(Configuration.VERSION_2_3_31);
        Template template = new Template("template", new StringReader(ftlContent), config);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> dataModel = objectMapper.readValue(jsonContent, Map.class);
        

        template.process(dataModel, writer);
        return writer.toString();
}
	
	public String generateJsontoJsonCreatebydata(String jsonContent) throws IOException, TemplateException  {

		String ftlContent = new String(
				Files.readAllBytes(Paths.get("D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\toXML_create.ftl")));

		String mockUrl = "http://bsit-srv04:8003/tiplus2-deploy2/hello";
		String xmlOutput = jjService.processTemplate(jsonContent, ftlContent);
		logger.info(xmlOutput);

		XmlMapper xmlMapper = new XmlMapper();

		JsonNode jsonNode = xmlMapper.readTree(xmlOutput);

		ObjectMapper objectMapper = new ObjectMapper();

		String jsonData = objectMapper.writeValueAsString(jsonNode);

		JsonNode rootNode = objectMapper.readTree(jsonData);

		String theirReference = rootNode.path("TFCPCCRT").path("Context").path("TheirReference").asText();

		String dataMasterID = dataForMasterID();
		logger.info(theirReference);

		String ftlFilePath = new String(
				Files.readAllBytes(Paths.get("D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\toJson_create.ftl")));
		

	return JsonTOJsonService.processMockApi(mockUrl, xmlOutput, ftlFilePath, theirReference, dataMasterID);

	}
	 @PostMapping("/dataforjson")
	    public String createCustomer(@RequestBody Dataforjson customerRequest) throws IOException, TemplateException {
	      
	        System.out.println("Received data: " + customerRequest);
	        ObjectMapper objectMapper = new ObjectMapper();
	        String jsonString = objectMapper.writeValueAsString(customerRequest);
	        System.out.println(jsonString);
	        String jsondata =generateJson(jsonString);

	        
	        return generateJsontoJsonCreatebydata(jsondata);
	    }
	 
	 //List input
	 public String generateListJson(String jsonContent) throws IOException, TemplateException  {
			String ftlContent = new String(Files.readAllBytes(Paths.get("D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\inputDataJson_list.ftl")));

			StringWriter writer = new StringWriter();
		      

			Configuration config = new Configuration(Configuration.VERSION_2_3_31);
	        Template template = new Template("template", new StringReader(ftlContent), config);
	        ObjectMapper objectMapper = new ObjectMapper();
	        Map<String, Object> dataModel = objectMapper.readValue(jsonContent, Map.class);
	        

	        template.process(dataModel, writer);
	        return writer.toString();
	}
		
		public String generateJsontoJsonListbydata(String jsonContent) throws IOException, TemplateException  {

			String ftlContent = new String(
					Files.readAllBytes(Paths.get("D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\toXML_list.ftl")));

			String mockUrl = "http://bsit-srv04:8003/tiplus2-deploy2/hello";
			String xmlOutput = jjService.processTemplate(jsonContent, ftlContent);
			logger.info(xmlOutput);

			XmlMapper xmlMapper = new XmlMapper();

			JsonNode jsonNode = xmlMapper.readTree(xmlOutput);

			ObjectMapper objectMapper = new ObjectMapper();

			String jsonData = objectMapper.writeValueAsString(jsonNode);

			JsonNode rootNode = objectMapper.readTree(jsonData);

			String theirReference = rootNode.path("TFCPCCRT").path("Context").path("TheirReference").asText();

			String dataMasterID = dataForMasterID();
			logger.info(theirReference);

			String ftlFilePath = new String(
					Files.readAllBytes(Paths.get("D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\toJson_list.ftl")));
			

		return JsonTOJsonService.processMockApi(mockUrl, xmlOutput, ftlFilePath, theirReference, dataMasterID);

		}
		 @PostMapping("/dataforListjson")
		    public String customerList(@RequestBody ListInputJson customerRequest) throws IOException, TemplateException {
		      
		        System.out.println("Received data: " + customerRequest);
		        ObjectMapper objectMapper = new ObjectMapper();
		        String jsonString = objectMapper.writeValueAsString(customerRequest);
		        System.out.println(jsonString);
		        String jsondata =generateListJson(jsonString);

		        
		        return generateJsontoJsonListbydata(jsondata);
		    }
		 
		 //Detail input
		 public String generateDetailJson(String jsonContent) throws IOException, TemplateException  {
				String ftlContent = new String(Files.readAllBytes(Paths.get("D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\inputDataJson_Detail.ftl")));

				StringWriter writer = new StringWriter();
			      

				Configuration config = new Configuration(Configuration.VERSION_2_3_31);
		        Template template = new Template("template", new StringReader(ftlContent), config);
		        ObjectMapper objectMapper = new ObjectMapper();
		        Map<String, Object> dataModel = objectMapper.readValue(jsonContent, Map.class);
		        

		        template.process(dataModel, writer);
		        return writer.toString();
		}
			
			public String generateJsontoJsonDetailbydata(String jsonContent) throws IOException, TemplateException  {

				String ftlContent = new String(
						Files.readAllBytes(Paths.get("D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\toXML_detail.ftl")));

				String mockUrl = "http://bsit-srv04:8003/tiplus2-deploy2/hello";
				String xmlOutput = jjService.processTemplate(jsonContent, ftlContent);
				logger.info(xmlOutput);

				XmlMapper xmlMapper = new XmlMapper();

				JsonNode jsonNode = xmlMapper.readTree(xmlOutput);

				ObjectMapper objectMapper = new ObjectMapper();

				String jsonData = objectMapper.writeValueAsString(jsonNode);

				JsonNode rootNode = objectMapper.readTree(jsonData);

				String theirReference = rootNode.path("TFCPCCRT").path("Context").path("TheirReference").asText();

				String dataMasterID = dataForMasterID();
				logger.info(theirReference);

				String ftlFilePath = new String(
						Files.readAllBytes(Paths.get("D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\toJson_detail.ftl")));
				

			return JsonTOJsonService.processMockApi(mockUrl, xmlOutput, ftlFilePath, theirReference, dataMasterID);

			}
			 @PostMapping("/inputFormDetailjson")
			    public String customerDetail(@RequestBody DetailInputJson customerRequest) throws IOException, TemplateException {
			      
			        System.out.println("Received data: " + customerRequest);
			        ObjectMapper objectMapper = new ObjectMapper();
			        String jsonString = objectMapper.writeValueAsString(customerRequest);
			        System.out.println(jsonString);
			        String jsondata =generateDetailJson(jsonString);

			        
			        return generateJsontoJsonDetailbydata(jsondata);
			    }

	 
}