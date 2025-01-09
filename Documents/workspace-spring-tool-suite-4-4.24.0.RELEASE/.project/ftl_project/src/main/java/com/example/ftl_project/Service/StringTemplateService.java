package com.example.ftl_project.Service;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class StringTemplateService {
	
	private final DatabaseService databaseService;
	private static final Logger logger = Logger.getLogger(JsonTOJsonService.class.getName());

	@Autowired
	public StringTemplateService(DatabaseService databaseService) {
		super();
		this.databaseService = databaseService;
	}
	
	public static String generateCorrelationId() {
		UUID uuid = UUID.randomUUID();

		return uuid.toString();
	}
	
	 public String fetchTheirReference(JsonNode rootNode) {
	        return rootNode.path("TFCPCCRT").path("Context").path("TheirReference").asText();
	    }
	 
	 public String fetchDataMasterID() {
	        List<String> masterRefId = databaseService.fetchMasterRefIdList();
	        if (masterRefId.isEmpty()) {
	            logger.warning("Master reference ID list is empty.");
	            return null;
	        }
	        return masterRefId.get(0);
	    }
	
	
	 public String generateCorrelationIdAndAddToData(Map<String, Object> dataModel) {
	        String correlationId = generateCorrelationId();
	        dataModel.put("correlationId", correlationId);
	        return correlationId;
	    }

	 public static String transformXmlToJson(String xmlResponse, String ftlFilePath, String theirReference, String dataMasterId) throws IOException, TemplateException {
	        XmlMapper xmlMapper = new XmlMapper();
	        Map<String, Object> data = xmlMapper.readValue(xmlResponse, Map.class);

	        ObjectMapper objectMapper = new ObjectMapper();
	        String jsonContent = objectMapper.writeValueAsString(data);

	        StringWriter out = new StringWriter();
	        Configuration config = new Configuration(Configuration.VERSION_2_3_32);
	        Template template = new Template("template", new StringReader(ftlFilePath), config);

	        Map<String, Object> dataModel = objectMapper.readValue(jsonContent, Map.class);
	        dataModel.put("theirReference", theirReference);
	        dataModel.put("masterId", dataMasterId);

	        template.process(dataModel, out);

	        return out.toString();
	    }

//	 public String processTemplate(String jsonContent, String ftlContent) throws IOException, TemplateException {
//	        StringWriter writer = new StringWriter();
//	      
//	            Configuration config = new Configuration(Configuration.VERSION_2_3_31);
//	            Template template = new Template("template", new StringReader(ftlContent), config);
//	            ObjectMapper objectMapper = new ObjectMapper();
//	            Map<String, Object> dataModel = objectMapper.readValue(jsonContent, Map.class);
//
//	            generateCorrelationIdAndAddToData(dataModel);
//	            String masterRefId = fetchDataMasterID();
//	            dataModel.put("masterReferenceId", masterRefId);
//
//	            template.process(dataModel, writer);
//	            return writer.toString();
//	    }
	 
	 public String processTemplateImport(String jsonContent, String filePathImport)throws IOException, TemplateException  {
	     StringWriter writer = new StringWriter();

		 Configuration config=new Configuration(Configuration.VERSION_2_3_31);
		 
         config.setDirectoryForTemplateLoading(new File("D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\include\\"));
	 
		 Template template=config.getTemplate(filePathImport);
		 
		 ObjectMapper objectMapper=new ObjectMapper();
		 Map<String,Object> dataModel=objectMapper.readValue(jsonContent,Map.class);
		 
		 generateCorrelationIdAndAddToData(dataModel);
         String masterRefId = fetchDataMasterID();
         dataModel.put("masterReferenceId", masterRefId);

         template.process(dataModel, writer);
	     return writer.toString();

	 }
	 
	 
	 
	 public String processTemplate(String jsonContent, String filePath_head,String filePath_content)  throws IOException, TemplateException  {
	        StringWriter writer = new StringWriter();
	       
	            Configuration config = new Configuration(Configuration.VERSION_2_3_31);

//	            config.setDirectoryForTemplateLoading(new File("D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\include"));
//
//	            Template template_1 =config.getTemplate(filePath_head);
//	            Template template_2 =config.getTemplate(filePath_content);
	            Template template = new Template("template", new StringReader(filePath_head), config);
	            Template template1 = new Template("template", new StringReader(filePath_content), config);


	            ObjectMapper objectMapper = new ObjectMapper();
	            Map<String, Object> dataModel = objectMapper.readValue(jsonContent, Map.class);

	            generateCorrelationIdAndAddToData(dataModel);
	            String masterRefId = fetchDataMasterID();
	            dataModel.put("masterReferenceId", masterRefId);

	            template.process(dataModel, writer);
	            template1.process(dataModel, writer);

	       
	        return writer.toString();
	    }

	 public static String processMockApi(String url, String requestBody, String ftlFilePath, String theirReference, String dataMasterId) throws TemplateException, RestClientException,IOException {
		    
		        RestTemplate restTemplate = new RestTemplate();
		        HttpHeaders headers = new HttpHeaders();
		        headers.add("Content-Type", "application/xml");
		        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

		        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

		        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
		            String xmlResponse = response.getBody();
		            return transformXmlToJson(xmlResponse, ftlFilePath, theirReference, dataMasterId);
		        } else {
		        	logger.warning("Invalid response from API: " + response.getStatusCode());
		        	return "Invalid response from API";
		        }
		}

}
