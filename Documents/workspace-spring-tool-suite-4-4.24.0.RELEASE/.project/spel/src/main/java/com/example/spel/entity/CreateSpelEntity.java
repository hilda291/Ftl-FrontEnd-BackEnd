package com.example.spel.entity;

import java.util.Map;

public class CreateSpelEntity {

	private Map<String, String> sendersReference,orderingCustomer,beneficiaryCustomer,modeOfTransmission,detailsOfCharges;
	private Map<String,Object> valueDateCurrencyInterbankSettledAmount,currencyInstructedAmount;
	
	public Map<String, String> getSendersReference() {
		return sendersReference;
	}
	public void setSendersReference(Map<String, String> sendersReference) {
		this.sendersReference = sendersReference;
	}
	public Map<String, String> getOrderingCustomer() {
		return orderingCustomer;
	}
	public void setOrderingCustomer(Map<String, String> orderingCustomer) {
		this.orderingCustomer = orderingCustomer;
	}
	public Map<String, String> getBeneficiaryCustomer() {
		return beneficiaryCustomer;
	}
	public void setBeneficiaryCustomer(Map<String, String> beneficiaryCustomer) {
		this.beneficiaryCustomer = beneficiaryCustomer;
	}
	public Map<String, String> getModeOfTransmission() {
		return modeOfTransmission;
	}
	public void setModeOfTransmission(Map<String, String> modeOfTransmission) {
		this.modeOfTransmission = modeOfTransmission;
	}
	public Map<String, String> getDetailsOfCharges() {
		return detailsOfCharges;
	}
	public void setDetailsOfCharges(Map<String, String> detailsOfCharges) {
		this.detailsOfCharges = detailsOfCharges;
	}
	public Map<String, Object> getValueDateCurrencyInterbankSettledAmount() {
		return valueDateCurrencyInterbankSettledAmount;
	}
	public void setValueDateCurrencyInterbankSettledAmount(Map<String, Object> valueDateCurrencyInterbankSettledAmount) {
		this.valueDateCurrencyInterbankSettledAmount = valueDateCurrencyInterbankSettledAmount;
	}
	public Map<String, Object> getCurrencyInstructedAmount() {
		return currencyInstructedAmount;
	}
	public void setCurrencyInstructedAmount(Map<String, Object> currencyInstructedAmount) {
		this.currencyInstructedAmount = currencyInstructedAmount;
	}
	
}
