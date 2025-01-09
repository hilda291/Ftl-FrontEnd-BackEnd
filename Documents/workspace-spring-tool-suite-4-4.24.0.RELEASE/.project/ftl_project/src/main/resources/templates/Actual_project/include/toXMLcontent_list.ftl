<m:SearchMastersRequest>
		<m:DefinitionID>ORMSEARCH</m:DefinitionID>
		<m:Customer>
			<c:SourceBankingBusiness>MBWW</c:SourceBankingBusiness>
			<c:Mnemonic>${customerId}</c:Mnemonic>
		</m:Customer>
		<#list filters as filter>
				<m:Status>${filter.status}</m:Status>
		</#list>
	</m:SearchMastersRequest>
</ServiceRequest>