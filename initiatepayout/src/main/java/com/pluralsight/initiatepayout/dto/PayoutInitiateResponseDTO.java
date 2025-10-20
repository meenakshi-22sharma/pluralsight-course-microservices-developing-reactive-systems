package com.pluralsight.initiatepayout.dto;

public class PayoutInitiateResponseDTO {
    
    private final String requestId;
    private final String status;
    private final String initiatedAt;

    public PayoutInitiateResponseDTO(String requestId, String status, String initiatedAt) {
        this.requestId = requestId;
        this.status = status;
        this.initiatedAt = initiatedAt;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getStatus() {
        return status;
    }

    public String getInitiatedAt() {
        return initiatedAt;
    }

    
}
