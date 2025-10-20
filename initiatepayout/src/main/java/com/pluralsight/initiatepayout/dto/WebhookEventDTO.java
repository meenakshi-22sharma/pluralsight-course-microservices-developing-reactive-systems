package com.pluralsight.initiatepayout.dto;

public class WebhookEventDTO {
    private String type;

    private WebhookData data;

    public static class WebhookData{
        private String id;
        private String status;

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

    public WebhookEventDTO(){}

    public WebhookEventDTO(String type, WebhookData data){
        this.type = type;
        this.data = data;
    }

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
    
}
