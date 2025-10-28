package com.pluralsight.finservice.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pluralsight.finservice.dto.PayoutInitiatedEventDTO;
import com.pluralsight.finservice.dto.TazapayRequestDTO;
import com.pluralsight.finservice.dto.TazapayResponseDTO;
import com.pluralsight.finservice.model.PayoutStatus;
import com.pluralsight.finservice.repo.PayoutStatusRepo;
import com.pluralsight.finservice.service.TazapayService;

@Component
public class PayoutConsumer {

    private static final Logger log = LoggerFactory.getLogger(PayoutConsumer.class);
    private final ObjectMapper objectMapper;
    private final TazapayService tazapayService;
    private final PayoutStatusRepo payoutStatusRepo;

    public PayoutConsumer(TazapayService tazapayService, ObjectMapper objectMapper, PayoutStatusRepo payoutStatusRepo) {
        this.tazapayService = tazapayService;
        this.objectMapper = objectMapper;
        this.payoutStatusRepo = payoutStatusRepo;
    }

    @KafkaListener(topics = "payoutInitiated", groupId = "finservice-group")
    public void handlePayoutInitiated(String message){

        try {
            log.info("Received payout initiated event: {}", message);
            
            // Parse the Kafka message as PayoutInitiatedEvent
            PayoutInitiatedEventDTO payoutEvent = objectMapper.readValue(message, PayoutInitiatedEventDTO.class);
            
            log.info("Processing payout for requestId: {}", payoutEvent.getRequestId());

            TazapayRequestDTO tazapayRequestDTO = convertToTazapayRequest(payoutEvent);

            tazapayService.processPayout(tazapayRequestDTO)
                    .subscribe(
                            response -> {
                                log.info("Payout processed successfully for requestId: {}, TazapayId: {}",
                                        payoutEvent.getRequestId(), response.getData().getId());
                                
                                // Log the full Tazapay response
                                try {
                                    String responseJson = objectMapper.writeValueAsString(response);
                                    log.info("Tazapay Response: {}", responseJson);
                                } catch (Exception e) {
                                    log.error("Failed to serialize Tazapay response", e);
                                }
                                
                                updatePayoutStatus(payoutEvent.getRequestId(), response);
                            },
                            error -> log.error("Failed to process payout for requestId: {}", payoutEvent.getRequestId(), error)
                    );
            
        } catch (Exception e) {
            log.error("Error processing payout initiated event", e);
        }
    }

    private void updatePayoutStatus(String requestId, TazapayResponseDTO response) {
        try {
            TazapayResponseDTO.Data data = response.getData();
            String tazapayId = data.getId();
            String currency = data.getCurrency();
            
            PayoutStatus payoutStatus = new PayoutStatus();
            payoutStatus.setReferenceId(requestId);
            payoutStatus.setTazapayId(tazapayId);
            payoutStatus.setStatus("PENDING");
            payoutStatus.setCreatedAt(java.time.OffsetDateTime.now());
            
            // Only update FX data if payoutFxTransaction is not null
            if (data.getPayoutFxTransaction() != null) {
                String finalCurrency = data.getPayoutFxTransaction().getFinalAmount().getCurrency();
                String currencyCode = currency + "/" + finalCurrency;
                Double fxRate = data.getPayoutFxTransaction().getExchangeRate();
                Integer baseAmount = data.getPayoutFxTransaction().getFinalAmount().getAmount();
                
                payoutStatus.setCurrencyCode(currencyCode);
                payoutStatus.setFxRate(fxRate);
                payoutStatus.setBaseAmount(baseAmount);
                
                log.info("Updated payout status for requestId: {} with TazapayId: {}, CurrencyCode: {}, FxRate: {}, BaseAmount: {}", 
                        requestId, tazapayId, currencyCode, fxRate, baseAmount);
            } else {
                // Set basic currency info without FX data
                payoutStatus.setCurrencyCode(currency);
                log.info("Updated payout status for requestId: {} with TazapayId: {}, Currency: {} (no FX data available)", 
                        requestId, tazapayId, currency);
            }
            
            payoutStatusRepo.save(payoutStatus);
        } catch (Exception e) {
            log.error("Failed to update payout status for requestId: {}", requestId, e);
        }
    }

    private TazapayRequestDTO convertToTazapayRequest(PayoutInitiatedEventDTO event) {
        TazapayRequestDTO request = new TazapayRequestDTO();
        request.setAmount(event.getAmount() != null ? event.getAmount().intValue() : null);
        request.setCurrency(event.getCurrency());
        request.setPurpose(event.getPurpose());
        request.setTransactionDescription(event.getTransactionDescription());
        
        if (event.getBeneficiaryDetails() != null) {
            TazapayRequestDTO.BeneficiaryDetails beneficiaryDetails = new TazapayRequestDTO.BeneficiaryDetails();
            beneficiaryDetails.setName(event.getBeneficiaryDetails().getName());
            beneficiaryDetails.setType(event.getBeneficiaryDetails().getType());
            
            if (event.getBeneficiaryDetails().getAddress() != null) {
                TazapayRequestDTO.Address address = new TazapayRequestDTO.Address();
                address.setLine1(event.getBeneficiaryDetails().getAddress().getLine1());
                address.setCity(event.getBeneficiaryDetails().getAddress().getCity());
                address.setState(event.getBeneficiaryDetails().getAddress().getState());
                address.setCountry(event.getBeneficiaryDetails().getAddress().getCountry());
                address.setPostalCode(event.getBeneficiaryDetails().getAddress().getPostalCode());
                beneficiaryDetails.setAddress(address);
            }
            
            if (event.getBeneficiaryDetails().getDestinationDetails() != null) {
                TazapayRequestDTO.DestinationDetails destinationDetails = new TazapayRequestDTO.DestinationDetails();
                destinationDetails.setType(event.getBeneficiaryDetails().getDestinationDetails().getType());
                
                if (event.getBeneficiaryDetails().getDestinationDetails().getBank() != null) {
                    TazapayRequestDTO.Bank bank = new TazapayRequestDTO.Bank();
                    bank.setAccountNumber(event.getBeneficiaryDetails().getDestinationDetails().getBank().getAccountNumber());
                    bank.setBankName(event.getBeneficiaryDetails().getDestinationDetails().getBank().getBankName());
                    bank.setCountry(event.getBeneficiaryDetails().getDestinationDetails().getBank().getCountry());
                    bank.setCurrency(event.getBeneficiaryDetails().getDestinationDetails().getBank().getCurrency());
                    
                    if (event.getBeneficiaryDetails().getDestinationDetails().getBank().getBankCodes() != null) {
                        TazapayRequestDTO.BankCodes bankCodes = new TazapayRequestDTO.BankCodes();
                        bankCodes.setIfscCode(event.getBeneficiaryDetails().getDestinationDetails().getBank().getBankCodes().getIfscCode());
                        bank.setBankCodes(bankCodes);
                    }
                    
                    destinationDetails.setBank(bank);
                }
                
                beneficiaryDetails.setDestinationDetails(destinationDetails);
            }
            
            request.setBeneficiaryDetails(beneficiaryDetails);
        }
        
        return request;
    }

}
