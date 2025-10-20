package com.pluralsight.initiatepayout.service;

import java.time.Instant;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pluralsight.initiatepayout.dto.PayoutInitiateRequestDTO;
import com.pluralsight.initiatepayout.dto.PayoutInitiateResponseDTO;
import com.pluralsight.initiatepayout.model.PayoutStatus;
import com.pluralsight.initiatepayout.repo.PayoutStatusRepo;

import java.util.UUID;
import java.util.Optional;

@Service
public class PayoutService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;
    private final PayoutStatusRepo payoutStatusRepo;

    public PayoutService(KafkaTemplate<String, String> kafkaTemplate,
            @org.springframework.beans.factory.annotation.Value("${initiatepayout.topic}") String topic,
            PayoutStatusRepo payoutStatusRepo) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
        this.payoutStatusRepo = payoutStatusRepo;
    }

    public PayoutInitiateResponseDTO initiateExternalPayout(PayoutInitiateRequestDTO payload) {
        String requestID = payload.getRequestId();
        payload.setRequestId(requestID);
        
        String value;
        try {
            value = new ObjectMapper().writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize payout request", e);
        }

        PayoutStatus entity = new PayoutStatus();
        entity.setReferenceId(requestID);
        entity.setStatus("INITIATED");
        entity.setCreatedAt(java.time.OffsetDateTime.now());
        payoutStatusRepo.save(entity);

        kafkaTemplate.send(topic, requestID, value);
        return new PayoutInitiateResponseDTO(requestID, "INITIATED", Instant.now().toString());
    }

    public PayoutInitiateResponseDTO getPayoutStatus(String referenceId) {
        Optional<PayoutStatus> payoutStatus = payoutStatusRepo.findById(referenceId);
        
        if (payoutStatus.isPresent()) {
            PayoutStatus status = payoutStatus.get();
            return new PayoutInitiateResponseDTO(
                    status.getReferenceId(),
                    status.getStatus(),
                    status.getCreatedAt().toString()
            );
        } else {
            // Return a response indicating the reference ID was not found
            return new PayoutInitiateResponseDTO(
                    referenceId,
                    "NOT_FOUND",
                    Instant.now().toString()
            );
        }
    }
}
