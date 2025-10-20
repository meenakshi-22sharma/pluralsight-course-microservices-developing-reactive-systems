package com.pluralsight.initiatepayout.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pluralsight.initiatepayout.dto.WebhookEventDTO;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/webhook")
public class WebhookController {
    
    private static final Logger log = LoggerFactory.getLogger(WebhookController.class);
    
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String webhookTopic;
    
    public WebhookController(ObjectMapper objectMapper, 
                           KafkaTemplate<String, String> kafkaTemplate,
                           @org.springframework.beans.factory.annotation.Value("${webhook.topic}") String webhookTopic) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
        this.webhookTopic = webhookTopic;
    }

    @PostMapping(value = "/tazapay", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<String>> handleTazapayWebhook(@RequestBody String payload) {
        log.info("Received Tazapay webhook payload: {}", payload);
        
        try {
            WebhookEventDTO webhookEvent = objectMapper.readValue(payload, WebhookEventDTO.class);
            WebhookEventDTO.WebhookData data = webhookEvent.getData();
            
            String payoutId = data.getId();
            String status = data.getStatus();
            
            log.info("Processing webhook for payout ID: {} with status: {}", payoutId, status);
            
            String webhookEventJson = objectMapper.writeValueAsString(webhookEvent);
            kafkaTemplate.send(webhookTopic, payoutId, webhookEventJson);
            
            log.info("Successfully sent webhook event to Kafka topic: {} for payout ID: {}", webhookTopic, payoutId);
            
            return Mono.just(ResponseEntity.ok("OK"));
            
        } catch (Exception e) {
            log.error("Error processing webhook payload", e);
            return Mono.just(ResponseEntity.status(500).body("Error processing webhook"));
        }
    }
}
