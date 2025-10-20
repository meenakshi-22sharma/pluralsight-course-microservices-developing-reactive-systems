package com.pluralsight.initiatepayout.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public class PayoutInitiateRequestDTO {

    @NotBlank
    private String requestId;

    @NotNull
    @Positive
    private Number amount;

    @NotBlank
    private String currency;

    @NotBlank
    private String purpose;

    @Size(max = 256)
    private String transaction_description;

    @NotNull
    @Valid
    private BeneficiaryDetails beneficiary_details;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }


    public Number getAmount() {
        return amount;
    }

    public void setAmount(Number amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getTransaction_description() {
        return transaction_description;
    }

    public void setTransaction_description(String transaction_description) {
        this.transaction_description = transaction_description;
    }

    public BeneficiaryDetails getBeneficiary_details() {
        return beneficiary_details;
    }

    public void setBeneficiary_details(BeneficiaryDetails beneficiary_details) {
        this.beneficiary_details = beneficiary_details;
    }

    public static class BeneficiaryDetails {
        @NotBlank
        private String name;

        @NotBlank
        private String type;

        @NotNull
        @Valid
        private Address address;

        @NotNull
        @Valid
        private DestinationDetails destination_details;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public Address getAddress() { return address; }
        public void setAddress(Address address) { this.address = address; }
        public DestinationDetails getDestination_details() { return destination_details; }
        public void setDestination_details(DestinationDetails destination_details) { this.destination_details = destination_details; }
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
        private String postal_code;

        public String getLine1() { return line1; }
        public void setLine1(String line1) { this.line1 = line1; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getState() { return state; }
        public void setState(String state) { this.state = state; }
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        public String getPostal_code() { return postal_code; }
        public void setPostal_code(String postal_code) { this.postal_code = postal_code; }
    }

    public static class DestinationDetails {
        @NotBlank
        private String type;

        @Valid
        private Bank bank;

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public Bank getBank() { return bank; }
        public void setBank(Bank bank) { this.bank = bank; }
    }

    public static class Bank {
        @NotNull
        @Valid
        private BankCodes bank_codes;

        @NotBlank
        private String account_number;

        @NotBlank
        private String bank_name;

        @NotBlank
        private String country;

        @NotBlank
        private String currency;

        public BankCodes getBank_codes() { return bank_codes; }
        public void setBank_codes(BankCodes bank_codes) { this.bank_codes = bank_codes; }
        public String getAccount_number() { return account_number; }
        public void setAccount_number(String account_number) { this.account_number = account_number; }
        public String getBank_name() { return bank_name; }
        public void setBank_name(String bank_name) { this.bank_name = bank_name; }
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        public String getCurrency() { return currency; }
        public void setCurrency(String currency) { this.currency = currency; }
    }

    public static class BankCodes {
        @NotBlank
        private String ifsc_code;

        public String getIfsc_code() { return ifsc_code; }
        public void setIfsc_code(String ifsc_code) { this.ifsc_code = ifsc_code; }
    }
}
