package com.example.ftl_project.entity;

public class Dataforjson {
	private String theirReferenceId;
    private String city;
    private String customerName;
    private Double amount;
    private String currency;
	public String getTheirReferenceId() {
		return theirReferenceId;
	}
	public void setTheirReferenceId(String theirReferenceId) {
		this.theirReferenceId = theirReferenceId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Dataforjson(String theirReferenceId, String city, String customerName, Double amount, String currency) {
		super();
		this.theirReferenceId = theirReferenceId;
		this.city = city;
		this.customerName = customerName;
		this.amount = amount;
		this.currency = currency;
	}
	public Dataforjson() {
		super();
	}
	@Override
	public String toString() {
		return "Dataforjson [theirReferenceId=" + theirReferenceId + ", city=" + city + ", customerName=" + customerName
				+ ", amount=" + amount + ", currency=" + currency + "]";
	}
    
    
}