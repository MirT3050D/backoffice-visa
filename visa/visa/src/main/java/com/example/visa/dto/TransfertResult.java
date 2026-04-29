package com.example.visa.dto;

public class TransfertResult {
    private final Long visaId;
    private final Long passeportId;

    public TransfertResult(Long visaId, Long passeportId) {
        this.visaId = visaId;
        this.passeportId = passeportId;
    }

    public Long getVisaId() {
        return visaId;
    }

    public Long getPasseportId() {
        return passeportId;
    }
}
