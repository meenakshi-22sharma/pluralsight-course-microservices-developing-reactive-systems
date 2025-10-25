package com.pluralsight.finservice.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.pluralsight.finservice.dto.TazapayRequestDTO;
import com.pluralsight.finservice.dto.TazapayResponseDTO;

import reactor.core.publisher.Mono;

@Service
public class TazapayService {
    private static final Logger log = LoggerFactory.getLogger(TazapayService.class);
    
    private final WebClient webClient;
    private final String apiUrl;
    private final String authorization;
    
    public TazapayService(WebClient.Builder webClientBuilder,
                         @Value("${tazapay.api.url}") String apiUrl,
                         @Value("${tazapay.api.authorization}") String authorization) {
        this.webClient = webClientBuilder.build();
        this.apiUrl = apiUrl;
        this.authorization = authorization;
    }

    public Mono<TazapayResponseDTO> processPayout(TazapayRequestDTO request){
        log.info("Processing payout request for amount: {} {}", request.getAmount(), request.getCurrency());

        return webClient
                .post()
                .uri(apiUrl)
                .header("accept", "application/json")
                .header("authorization", authorization)
                .header("content-type", "application/json")
                .header("Idempotency-Key", request.getRequestId())                
                .bodyValue(request)
                .retrieve()
                .bodyToMono(TazapayResponseDTO.class)
                .doOnSuccess(response -> log.info("Tazapay API call successful. TxnId: {}, Status: {}", 
                                                response.getData().getId(), response.getStatus()))
                .doOnError(error -> log.error("Tazapay API call failed", error))
                .onErrorReturn(createErrorResponse());
    }

    private TazapayResponseDTO createErrorResponse() {
        TazapayResponseDTO errorResponse = new TazapayResponseDTO();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage("Failed to process payout with Tazapay API");
        return errorResponse;
    }
    
}
