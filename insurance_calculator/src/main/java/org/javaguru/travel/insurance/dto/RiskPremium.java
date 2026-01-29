package org.javaguru.travel.insurance.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.javaguru.travel.insurance.dto.util.BigDecimalSerializer;

import java.math.BigDecimal;

public record RiskPremium(
        String riskIc,
        @JsonSerialize(using = BigDecimalSerializer.class) BigDecimal premium
) {
}
