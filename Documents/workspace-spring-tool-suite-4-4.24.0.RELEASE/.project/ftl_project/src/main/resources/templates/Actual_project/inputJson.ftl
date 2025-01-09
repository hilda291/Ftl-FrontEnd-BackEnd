{
    "data": {
        "Remittance": {
            "sendersReference": "${theirReferenceId}",
            "valueDateCurrencyInterbankSettledAmount": {
                "valueDate": "2016-02-16",
                "currency": "USD",
                "interbankSettledAmount": 9500.55
            },
            "currencyInstructedAmount": {
                "currency": "${currency}",
                "amount": ${amount?string("0.##")}
            },
            "orderingCustomer": {
                "name": "${customerName}",
                "accountNumber": "",
                "address": "${city}"
            },
            "beneficiaryCustomer": {
                "name": "ABC",
                "accountNumber": "",
                "address": ""
            },
            "remittanceInformation": {
                "information": ""
            },
            "modeOfTransmission": [
                "SW"
            ],
            "detailsOfCharges": [
                "OUR"
            ],
            "additionaldata": {
                "transactionPurpose": ""
            }
        }
    },
    "documents": [
        {
            "metadata": {
                "mimeType": "",
                "extensionType": "",
                "attachment_id": "",
                "description": "",
                "filename": "",
                "type": "",
                "title": "",
                "exported_file_path": "",
                "doc_id": "",
                "dms_id": "",
                "filesize": "",
                "fileUom": "",
                "encrypted": ""
            },
            "docContent": {
                "base64Encoded": ""
            }
        }
    ]
}