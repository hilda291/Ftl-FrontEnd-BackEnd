{
	"Remittance": {
		"value": {
			"sendersReferenceId": "${theirReference}",
			"channelId": "${ResponseHeader.CorrelationId}",
			"masterReferenceId": "${masterId}",
			"status": "${ResponseHeader.Status}",
			"message": "${ResponseHeader.Details.Warning}"
		}
	}
}