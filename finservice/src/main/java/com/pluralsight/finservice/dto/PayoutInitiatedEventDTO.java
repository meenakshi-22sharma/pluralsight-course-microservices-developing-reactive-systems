package com.pluralsight.finservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PayoutInitiatedEventDTO {
    @JsonProperty("requestId")
    private String requestId;
    
    @JsonProperty("amount")
    private Number amount;
    
    @JsonProperty("currency")
    private String currency;
    
    @JsonProperty("purpose")
    private String purpose;
    
    @JsonProperty("transaction_description")
    private String transactionDescription;
    
    @JsonProperty("beneficiary_details")
    private BeneficiaryDetails beneficiaryDetails;
    
    // Getters and Setters
    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }
    public Number getAmount() { return amount; }
    public void setAmount(Number amount) { this.amount = amount; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
    public String getTransactionDescription() { return transactionDescription; }
    public void setTransactionDescription(String transactionDescription) { this.transactionDescription = transactionDescription; }
    public BeneficiaryDetails getBeneficiaryDetails() { return beneficiaryDetails; }
    public void setBeneficiaryDetails(BeneficiaryDetails beneficiaryDetails) { this.beneficiaryDetails = beneficiaryDetails; }
    
    // Inner classes
    public static class BeneficiaryDetails {
        @JsonProperty("name") private String name;
        @JsonProperty("type") private String type;
        @JsonProperty("address") private Address address;
        @JsonProperty("destination_details") private DestinationDetails destinationDetails;
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public Address getAddress() { return address; }
        public void setAddress(Address address) { this.address = address; }
        public DestinationDetails getDestinationDetails() { return destinationDetails; }
        public void setDestinationDetails(DestinationDetails destinationDetails) { this.destinationDetails = destinationDetails; }
    }
    
    public static class Address {
        @JsonProperty("line1") private String line1;
        @JsonProperty("city") private String city;
        @JsonProperty("state") private String state;
        @JsonProperty("country") private String country;
        @JsonProperty("postal_code") private String postalCode;
        
        public String getLine1() { return line1; }
        public void setLine1(String line1) { this.line1 = line1; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getState() { return state; }
        public void setState(String state) { this.state = state; }
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        public String getPostalCode() { return postalCode; }
        public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    }
    
    public static class DestinationDetails {
        @JsonProperty("type") private String type;
        @JsonProperty("bank") private Bank bank;
        
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public Bank getBank() { return bank; }
        public void setBank(Bank bank) { this.bank = bank; }
    }
    
    public static class Bank {
        @JsonProperty("bank_codes") private BankCodes bankCodes;
        @JsonProperty("account_number") private String accountNumber;
        @JsonProperty("bank_name") private String bankName;
        @JsonProperty("country") private String country;
        @JsonProperty("currency") private String currency;
        
        public BankCodes getBankCodes() { return bankCodes; }
        public void setBankCodes(BankCodes bankCodes) { this.bankCodes = bankCodes; }
        public String getAccountNumber() { return accountNumber; }
        public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
        public String getBankName() { return bankName; }
        public void setBankName(String bankName) { this.bankName = bankName; }
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        public String getCurrency() { return currency; }
        public void setCurrency(String currency) { this.currency = currency; }
    }
    
    public static class BankCodes {
        @JsonProperty("ifsc_code") private String ifscCode;
        
        public String getIfscCode() { return ifscCode; }
        public void setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }
    }
}
