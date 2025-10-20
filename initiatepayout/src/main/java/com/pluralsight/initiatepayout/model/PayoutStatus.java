package com.pluralsight.initiatepayout.model;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="payoutstatus")
public class PayoutStatus {
    
    @Id
    @Column(name = "referenceid", nullable = false, length = 100)
    private String referenceId;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "tazapayid", length = 100)
    private String tazapayId;

    @Column(name = "currencycode", length = 20)
    private String currencyCode;

    @Column(name = "fxrate")
    private Double fxRate;

    @Column(name = "baseamount")
    private Integer baseAmount;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTazapayId() {
        return tazapayId;
    }

    public void setTazapayId(String tazapayId) {
        this.tazapayId = tazapayId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Double getFxRate() {
        return fxRate;
    }

    public void setFxRate(Double fxRate) {
        this.fxRate = fxRate;
    }

    public Integer getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(Integer baseAmount) {
        this.baseAmount = baseAmount;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
