package com.example.ftl_project.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.example.ftl_project.Service.DatabaseService;
import com.example.ftl_project.Service.StringTemplateService;

import freemarker.template.TemplateException;

@RestController
@RequestMapping("/StringTemp")
@Component
public class StringTemplateController {
	


	private final StringTemplateService StringTemplateService;

	private final DatabaseService databaseService;
	
	 public StringTemplateController(StringTemplateService StringTemplateService, DatabaseService databaseService) {
		super();
		this.StringTemplateService = StringTemplateService;
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
	
	@PostMapping(value="/import", consumes="application/json", produces="application/xml")
	public String generateXMlWithImport(@RequestBody String jsonContent) throws RestClientException, TemplateException, IOException{
		
		String filePathImport = "import.ftl";

		String xmlOutput = StringTemplateService.processTemplateImport(jsonContent,filePathImport);

		logger.info(xmlOutput);
		return xmlOutput;

	}

	@PostMapping(value = "/headContent", consumes = "application/json", produces = "application/xml")
	public String generateXml(@RequestBody String jsonContent) throws RestClientException, TemplateException, IOException {
		
		String filePath = "\\toXml_create.ftl";


			String ftlContent = new String(Files.readAllBytes(Paths.get("D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\toXML_create.ftl")));

			String mockUrl = "http://bsit-srv04:8003/tiplus2-deploy2/hello";
		//	String xmlOutput = jjService.processTemplate(jsonContent, ftlContent);
			
			
			 String filePath_head="<?xml version=\"1.0\" standalone=\"yes\"?>\r\n"
	            		+ "<ServiceRequest xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:m=\"urn:messages.service.ti.apps.tiplus2.misys.com\" xmlns:c=\"urn:common.service.ti.apps.tiplus2.misys.com\" xmlns=\"urn:control.services.tiplus2.misys.com\">\r\n"
	            		+ "	<RequestHeader>\r\n"
	            		+ "		<Service>TI</Service>\r\n"
	            		+ "		<Operation>TFCPCCRT</Operation>\r\n"
	            		+ "		<Credentials>\r\n"
	            		+ "			<Name>LDS</Name>\r\n"
	            		+ "			<Password>Password</Password>\r\n"
	            		+ "			<Certificate>Certificate</Certificate>\r\n"
	            		+ "			<Digest>Digest</Digest>\r\n"
	            		+ "		</Credentials>\r\n"
	            		+ "		<ReplyFormat>FULL</ReplyFormat>\r\n"
	            		+ "		<ReplyTarget>ReplyTarget</ReplyTarget>  \r\n"
	            		+ "		<TargetSystem>ZONE2</TargetSystem>\r\n"
	            		+ "		<SourceSystem>API</SourceSystem>\r\n"
	            		+ "		<NoRepair>Y</NoRepair>\r\n"
	            		+ "		<NoOverride>Y</NoOverride>\r\n"
	            		+ "		<CorrelationId>${correlationId}</CorrelationId>\r\n"
	            		+ "		<TransactionControl>NONE</TransactionControl>\r\n"
	            		+ "	</RequestHeader>"
	            		+ "";
	            
	            String filePath_content="<ns2:TFCPCCRT xmlns:ns2=\"urn:messages.service.ti.apps.tiplus2.misys.com\" xmlns=\"urn:control.services.tiplus2.misys.com\" xmlns:ns4=\"urn:custom.service.ti.apps.tiplus2.misys.com\" xmlns:ns3=\"urn:common.service.ti.apps.tiplus2.misys.com\">\r\n"
	            		+ "		<ns2:Context>\r\n"
	            		+ "			<ns3:Branch>CITY</ns3:Branch>\r\n"
	            		+ "			<ns3:Customer>${data.Remittance.orderingCustomer.name}</ns3:Customer>\r\n"
	            		+ "			<ns3:Product>CPCI</ns3:Product>\r\n"
	            		+ "			<ns3:Event>PCIC</ns3:Event>\r\n"
	            		+ "			<ns3:TheirReference>${data.Remittance.sendersReference}</ns3:TheirReference>\r\n"
	            		+ "			<ns3:BehalfOfBranch>CITY</ns3:BehalfOfBranch>\r\n"
	            		+ "		</ns2:Context>\r\n"
	            		+ "		<ns2:Sender>\r\n"
	            		+ "			<ns3:Customer>${data.Remittance.orderingCustomer.name}</ns3:Customer>\r\n"
	            		+ "			<ns3:Reference>${data.Remittance.sendersReference}</ns3:Reference><#-- check in xml= TR-001002 -->\r\n"
	            		+ "		</ns2:Sender>\r\n"
	            		+ "		<ns2:Remitter>\r\n"
	            		+ "			<ns3:Customer>${data.Remittance.orderingCustomer.name}</ns3:Customer>\r\n"
	            		+ "			<ns3:Reference>${data.Remittance.sendersReference}</ns3:Reference>\r\n"
	            		+ "		</ns2:Remitter>\r\n"
	            		+ "		<ns2:RemitterAmount>\r\n"
	            		+ "			<ns3:Amount>${data.Remittance.currencyInstructedAmount.amount?string(\"0.##\")}</ns3:Amount>\r\n"
	            		+ "			<ns3:Currency>${data.Remittance.valueDateCurrencyInterbankSettledAmount.currency}</ns3:Currency>\r\n"
	            		+ "		</ns2:RemitterAmount>\r\n"
	            		+ "		<ns2:BeneficiaryAmount>\r\n"
	            		+ "			<ns3:Amount>${data.Remittance.currencyInstructedAmount.amount?string(\"0.##\")}</ns3:Amount>\r\n"
	            		+ "			<ns3:Currency>USD</ns3:Currency>\r\n"
	            		+ "		</ns2:BeneficiaryAmount>\r\n"
	            		+ "		<ns2:BenChargesFor>B</ns2:BenChargesFor>\r\n"
	            		+ "		<ns2:ReceiveDate>${data.Remittance.valueDateCurrencyInterbankSettledAmount.valueDate}</ns2:ReceiveDate>\r\n"
	            		+ "		<ns2:PaymentDate>${data.Remittance.valueDateCurrencyInterbankSettledAmount.valueDate}</ns2:PaymentDate>\r\n"
	            		+ "		<ns2:Beneficiary>\r\n"
	            		+ "			<ns3:NameAddress>1/${data.Remittance.beneficiaryCustomer.name}/US</ns3:NameAddress>\r\n"
	            		+ "		</ns2:Beneficiary>\r\n"
	            		+ "		<ns2:BenAccountNo>BENE</ns2:BenAccountNo>\r\n"
	            		+ "		<ns2:BeneficiaryBank>\r\n"
	            		+ "			<ns3:NameAddress>ABC</ns3:NameAddress>\r\n"
	            		+ "		</ns2:BeneficiaryBank>\r\n"
	            		+ "		<#list data.Remittance.modeOfTransmission as modeOfTransmissions>\r\n"
	            		+ "		<ns2:PaymentMethod>${modeOfTransmissions}</ns2:PaymentMethod>\r\n"
	            		+ "		</#list>\r\n"
	            		+ "		<ns2:eBankMasterRef/>\r\n"
	            		+ "	</ns2:TFCPCCRT>\r\n"
	            		+ "</ServiceRequest>";
	            
			String xmlOutput = StringTemplateService.processTemplate(jsonContent, filePath_head,filePath_content);

			logger.info(xmlOutput);

//			XmlMapper xmlMapper = new XmlMapper();
//
//			JsonNode jsonNode = xmlMapper.readTree(xmlOutput);
//
//			ObjectMapper objectMapper = new ObjectMapper();
//
//			String jsonData = objectMapper.writeValueAsString(jsonNode);
//
//			JsonNode rootNode = objectMapper.readTree(jsonData);
//
//			String theirReference = rootNode.path("TFCPCCRT").path("Context").path("TheirReference").asText();
//
//			String dataMasterID = dataForMasterID();
//			logger.info(theirReference);
//			
//			String ftlFilePath = new String(Files.readAllBytes(Paths.get("D:\\Bluescope\\.project\\ftl_project\\src\\main\\resources\\templates\\Actual_project\\toJson_create.ftl")));
//
//			return JsonTOJsonService.processMockApi(mockUrl, xmlOutput, ftlFilePath, theirReference, dataMasterID);		
//		
			return xmlOutput;
	}



}
