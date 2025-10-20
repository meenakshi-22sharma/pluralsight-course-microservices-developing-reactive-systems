package com.pluralsight.initiatepayout.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pluralsight.initiatepayout.dto.PayoutInitiateRequestDTO;
import com.pluralsight.initiatepayout.dto.PayoutInitiateResponseDTO;
import com.pluralsight.initiatepayout.service.PayoutService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/payout")
public class PayoutController {
    
    private final PayoutService payoutService;

    public PayoutController(PayoutService payoutService) {
        this.payoutService = payoutService;
    }

    @GetMapping("/{referenceId}")
    public ResponseEntity<PayoutInitiateResponseDTO> getPayoutStatus(@PathVariable String referenceId) {
        PayoutInitiateResponseDTO response = payoutService.getPayoutStatus(referenceId);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/initiate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PayoutInitiateResponseDTO> initiateExternal(@Valid @RequestBody PayoutInitiateRequestDTO payload) {
        PayoutInitiateResponseDTO response = payoutService.initiateExternalPayout(payload);
        return ResponseEntity.ok(response);
    }
}
