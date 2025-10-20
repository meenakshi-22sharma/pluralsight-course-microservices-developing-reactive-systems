package com.pluralsight.finservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TazapayResponseDTO {
    private String status;
    private String message;
    private Data data;
    
    // Constructors
    public TazapayResponseDTO() {}
    
    public TazapayResponseDTO(String status, String message, Data data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    
    // Getters and Setters
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Data getData() {
        return data;
    }
    
    public void setData(Data data) {
        this.data = data;
    }
    
    // Nested Data class with only essential fields
    public static class Data {
        private String id;
        private String currency;
        @JsonProperty("payout_fx_transaction")
        private PayoutFxTransaction payoutFxTransaction;
        
        // Constructors
        public Data() {}
        
        // Getters and Setters
        public String getId() {
            return id;
        }
        
        public void setId(String id) {
            this.id = id;
        }
        
        public String getCurrency() {
            return currency;
        }
        
        public void setCurrency(String currency) {
            this.currency = currency;
        }
        
        public PayoutFxTransaction getPayoutFxTransaction() {
            return payoutFxTransaction;
        }
        
        public void setPayoutFxTransaction(PayoutFxTransaction payoutFxTransaction) {
            this.payoutFxTransaction = payoutFxTransaction;
        }
    }
    
    // Payout FX Transaction class
    public static class PayoutFxTransaction {
        @JsonProperty("exchange_rate")
        private Double exchangeRate;

        @JsonProperty("final")
        private Amount finalAmount;
        
        // Constructors
        public PayoutFxTransaction() {}
        
        // Getters and Setters
        public Double getExchangeRate() {
            return exchangeRate;
        }
        
        public void setExchangeRate(Double exchangeRate) {
            this.exchangeRate = exchangeRate;
        }
        
        public Amount getFinalAmount() {
            return finalAmount;
        }
        
        public void setFinalAmount(Amount finalAmount) {
            this.finalAmount = finalAmount;
        }
    }
    
    // Amount class
    public static class Amount {
        private Integer amount;
        private String currency;
        
        // Constructors
        public Amount() {}
        
        // Getters and Setters
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
    }
}
