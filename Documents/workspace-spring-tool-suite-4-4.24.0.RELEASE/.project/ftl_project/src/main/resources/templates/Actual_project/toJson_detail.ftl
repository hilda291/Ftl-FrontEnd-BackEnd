{
	"RemittanceDetail": {
		"value": {
			"channelId": "${ResponseHeader.CorrelationId}",
			"customer": "${RetrieveMasterDetailResponse.ORMLOOKUP.Customer}",
			"isLive": "${RetrieveMasterDetailResponse.ORMLOOKUP.IsLive}",
			"payAccount": "${RetrieveMasterDetailResponse.ORMLOOKUP.PayAccount}",
			"masterReferenceId": "${RetrieveMasterDetailResponse.ORMLOOKUP.MasterReference}",
			"paymentStatus": "${RetrieveMasterDetailResponse.ORMLOOKUP.PaymentStatus}"
		}
	}
}