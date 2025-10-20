package com.pluralsight.finservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TazapayRequestDTO {
    @Valid
    @NotNull
    @JsonProperty("beneficiary_details")
    private BeneficiaryDetails beneficiaryDetails;
    
    @NotBlank
    private String purpose;
    
    @NotNull
    @Positive
    private Integer amount;
    
    @NotBlank
    private String currency;
    
    @NotBlank
    @JsonProperty("transaction_description")
    private String transactionDescription;
    
    // Constructors
    public TazapayRequestDTO() {}
    
    public TazapayRequestDTO(BeneficiaryDetails beneficiaryDetails, String purpose, 
                               Integer amount, String currency, String transactionDescription) {
        this.beneficiaryDetails = beneficiaryDetails;
        this.purpose = purpose;
        this.amount = amount;
        this.currency = currency;
        this.transactionDescription = transactionDescription;
    }
    
    // Getters and Setters
    public BeneficiaryDetails getBeneficiaryDetails() {
        return beneficiaryDetails;
    }
    
    public void setBeneficiaryDetails(BeneficiaryDetails beneficiaryDetails) {
        this.beneficiaryDetails = beneficiaryDetails;
    }
    
    public String getPurpose() {
        return purpose;
    }
    
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    
    public Integer getAmount() {
        return amount;
    }
    
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public String getTransactionDescription() {
        return transactionDescription;
    }
    
    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }
    
    // Nested classes
    public static class BeneficiaryDetails {
        @Valid
        @NotNull
        private Address address;
        
        @Valid
        @NotNull
        @JsonProperty("destination_details")
        private DestinationDetails destinationDetails;
        
        @NotBlank
        private String name;
        
        @NotBlank
        private String type;
        
        // Constructors
        public BeneficiaryDetails() {}
        
        public BeneficiaryDetails(Address address, DestinationDetails destinationDetails, 
                                 String name, String type) {
            this.address = address;
            this.destinationDetails = destinationDetails;
            this.name = name;
            this.type = type;
        }
        
        // Getters and Setters
        public Address getAddress() {
            return address;
        }
        
        public void setAddress(Address address) {
            this.address = address;
        }
        
        public DestinationDetails getDestinationDetails() {
            return destinationDetails;
        }
        
        public void setDestinationDetails(DestinationDetails destinationDetails) {
            this.destinationDetails = destinationDetails;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getType() {
            return type;
        }
        
        public void setType(String type) {
            this.type = type;
        }
    }
    
    public static class Address {
        @NotBlank
        private String line1;
        
        @NotBlank
        private String city;
        
        @NotBlank
        private String state;
        
        @NotBlank
        private String country;
        
        @NotBlank
        @JsonProperty("postal_code")
        private String postalCode;
        
        // Constructors
        public Address() {}
        
        public Address(String line1, String city, String state, String country, String postalCode) {
            this.line1 = line1;
            this.city = city;
            this.state = state;
            this.country = country;
            this.postalCode = postalCode;
        }
        
        // Getters and Setters
        public String getLine1() {
            return line1;
        }
        
        public void setLine1(String line1) {
            this.line1 = line1;
        }
        
        public String getCity() {
            return city;
        }
        
        public void setCity(String city) {
            this.city = city;
        }
        
        public String getState() {
            return state;
        }
        
        public void setState(String state) {
            this.state = state;
        }
        
        public String getCountry() {
            return country;
        }
        
        public void setCountry(String country) {
            this.country = country;
        }
        
        public String getPostalCode() {
            return postalCode;
        }
        
        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }
    }
    
    public static class DestinationDetails {
        @NotBlank
        private String type;
        
        @Valid
        @NotNull
        private Bank bank;
        
        // Constructors
        public DestinationDetails() {}
        
        public DestinationDetails(String type, Bank bank) {
            this.type = type;
            this.bank = bank;
        }
        
        // Getters and Setters
        public String getType() {
            return type;
        }
        
        public void setType(String type) {
            this.type = type;
        }
        
        public Bank getBank() {
            return bank;
        }
        
        public void setBank(Bank bank) {
            this.bank = bank;
        }
    }
    
    public static class Bank {
        @Valid
        @NotNull
        @JsonProperty("bank_codes")
        private BankCodes bankCodes;
        
        @NotBlank
        @JsonProperty("account_number")
        private String accountNumber;
        
        @NotBlank
        @JsonProperty("bank_name")
        private String bankName;
        
        @NotBlank
        private String country;
        
        @NotBlank
        private String currency;
        
        // Constructors
        public Bank() {}
        
        public Bank(BankCodes bankCodes, String accountNumber, String bankName, 
                   String country, String currency) {
            this.bankCodes = bankCodes;
            this.accountNumber = accountNumber;
            this.bankName = bankName;
            this.country = country;
            this.currency = currency;
        }
        
        // Getters and Setters
        public BankCodes getBankCodes() {
            return bankCodes;
        }
        
        public void setBankCodes(BankCodes bankCodes) {
            this.bankCodes = bankCodes;
        }
        
        public String getAccountNumber() {
            return accountNumber;
        }
        
        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }
        
        public String getBankName() {
            return bankName;
        }
        
        public void setBankName(String bankName) {
            this.bankName = bankName;
        }
        
        public String getCountry() {
            return country;
        }
        
        public void setCountry(String country) {
            this.country = country;
        }
        
        public String getCurrency() {
            return currency;
        }
        
        public void setCurrency(String currency) {
            this.currency = currency;
        }
    }
    
    public static class BankCodes {
        @NotBlank
        @JsonProperty("ifsc_code")
        private String ifscCode;
        
        // Constructors
        public BankCodes() {}
        
        public BankCodes(String ifscCode) {
            this.ifscCode = ifscCode;
        }
        
        // Getters and Setters
        public String getIfscCode() {
            return ifscCode;
        }
        
        public void setIfscCode(String ifscCode) {
            this.ifscCode = ifscCode;
        }
    }
}
