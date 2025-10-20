package com.pluralsight.finservice.consumer;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pluralsight.finservice.dto.WebhookEventDTO;
import com.pluralsight.finservice.model.PayoutStatus;
import com.pluralsight.finservice.repo.PayoutStatusRepo;

@Component
public class WebhookConsumer {
    
    private static final Logger log = LoggerFactory.getLogger(WebhookConsumer.class);
    
    private final ObjectMapper objectMapper;
    private final PayoutStatusRepo payoutStatusRepo;
    
    public WebhookConsumer(ObjectMapper objectMapper, PayoutStatusRepo payoutStatusRepository) {
        this.objectMapper = objectMapper;
        this.payoutStatusRepo = payoutStatusRepository;
    }

    @KafkaListener(topics = "tazapayWebhook", groupId = "finservice-group")
    public void handleWebhookEvent(String message) {
        log.info("Received webhook event: {}", message);
        
        try {
            // Parse the Kafka message as TazapayWebhookEvent
            WebhookEventDTO webhookEvent = objectMapper.readValue(message, WebhookEventDTO.class);
            WebhookEventDTO.WebhookData data = webhookEvent.getData();
            
            // Extract the payout ID and status from the data object
            String payoutId = data.getId();
            String status = data.getStatus();

            Optional<PayoutStatus> payoutStatusOpt = payoutStatusRepo.findByTazapayId(payoutId);
            if (payoutStatusOpt.isPresent()) {
                PayoutStatus payoutStatus = payoutStatusOpt.get();
                payoutStatus.setStatus(status.toUpperCase());
                
                payoutStatusRepo.save(payoutStatus);
                
                log.info("Updated payout status for tazapayId: {} to status: {}", payoutId, status);
            } else {
                log.warn("No payout status found for tazapayId: {}", payoutId);
            }
            
            log.info("Processing webhook for payout ID: {} with status: {}", payoutId, status);
            
        } catch (Exception e) {
            log.error("Error processing webhook event", e);
        }
    }
    
}
