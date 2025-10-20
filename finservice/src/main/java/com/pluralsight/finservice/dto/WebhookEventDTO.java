package com.pluralsight.finservice.dto;

public class WebhookEventDTO {
    private String type;
    private WebhookData data;
    
    // Constructors
    public WebhookEventDTO() {}
    
    // Getters and Setters
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public WebhookData getData() {
        return data;
    }
    
    public void setData(WebhookData data) {
        this.data = data;
    }
    
    public static class WebhookData {
        private String id;
        private String status;
        
        // Constructors
        public WebhookData() {}
        
        // Getters and Setters
        public String getId() {
            return id;
        }
        
        public void setId(String id) {
            this.id = id;
        }
        
        public String getStatus() {
            return status;
        }
        
        public void setStatus(String status) {
            this.status = status;
        }
    }    
}
