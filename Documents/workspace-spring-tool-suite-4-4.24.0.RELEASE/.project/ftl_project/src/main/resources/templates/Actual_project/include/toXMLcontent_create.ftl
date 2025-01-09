<#macro createContent>
<ns2:TFCPCCRT xmlns:ns2="urn:messages.service.ti.apps.tiplus2.misys.com" xmlns="urn:control.services.tiplus2.misys.com" xmlns:ns4="urn:custom.service.ti.apps.tiplus2.misys.com" xmlns:ns3="urn:common.service.ti.apps.tiplus2.misys.com">
		<ns2:Context>
			<ns3:Branch>CITY</ns3:Branch>
			<ns3:Customer>${data.Remittance.orderingCustomer.name}</ns3:Customer>
			<ns3:Product>CPCI</ns3:Product>
			<ns3:Event>PCIC</ns3:Event>
			<ns3:TheirReference>${data.Remittance.sendersReference}</ns3:TheirReference>
			<ns3:BehalfOfBranch>CITY</ns3:BehalfOfBranch>
		</ns2:Context>
		<ns2:Sender>
			<ns3:Customer>${data.Remittance.orderingCustomer.name}</ns3:Customer>
			<ns3:Reference>${data.Remittance.sendersReference}</ns3:Reference><#-- check in xml= TR-001002 -->
		</ns2:Sender>
		<ns2:Remitter>
			<ns3:Customer>${data.Remittance.orderingCustomer.name}</ns3:Customer>
			<ns3:Reference>${data.Remittance.sendersReference}</ns3:Reference>
		</ns2:Remitter>
		<ns2:RemitterAmount>
			<ns3:Amount>${data.Remittance.currencyInstructedAmount.amount?string("0.##")}</ns3:Amount>
			<ns3:Currency>${data.Remittance.valueDateCurrencyInterbankSettledAmount.currency}</ns3:Currency>
		</ns2:RemitterAmount>
		<ns2:BeneficiaryAmount>
			<ns3:Amount>${data.Remittance.currencyInstructedAmount.amount?string("0.##")}</ns3:Amount>
			<ns3:Currency>USD</ns3:Currency>
		</ns2:BeneficiaryAmount>
		<ns2:BenChargesFor>B</ns2:BenChargesFor>
		<ns2:ReceiveDate>${data.Remittance.valueDateCurrencyInterbankSettledAmount.valueDate}</ns2:ReceiveDate>
		<ns2:PaymentDate>${data.Remittance.valueDateCurrencyInterbankSettledAmount.valueDate}</ns2:PaymentDate>
		<ns2:Beneficiary>
			<ns3:NameAddress>1/${data.Remittance.beneficiaryCustomer.name}/US</ns3:NameAddress>
		</ns2:Beneficiary>
		<ns2:BenAccountNo>BENE</ns2:BenAccountNo>
		<ns2:BeneficiaryBank>
			<ns3:NameAddress>ABC</ns3:NameAddress>
		</ns2:BeneficiaryBank>
		<#list data.Remittance.modeOfTransmission as modeOfTransmissions>
		<ns2:PaymentMethod>${modeOfTransmissions}</ns2:PaymentMethod>
		</#list>
		<ns2:eBankMasterRef/>
	</ns2:TFCPCCRT>
</ServiceRequest>
</#macro>
<#>