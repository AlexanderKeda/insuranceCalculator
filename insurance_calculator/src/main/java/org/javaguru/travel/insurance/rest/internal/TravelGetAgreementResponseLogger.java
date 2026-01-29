package org.javaguru.travel.insurance.rest.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.javaguru.travel.insurance.dto.internal.TravelGetAgreementResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TravelGetAgreementResponseLogger {
    private static final Logger logger = LoggerFactory.getLogger(TravelGetAgreementResponseLogger.class);
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    void log(TravelGetAgreementResponse response) {
        try {
            String json = objectMapper.writeValueAsString(response);
            logger.info("RESPONSE: {}", json);
        } catch (JsonProcessingException e) {
            logger.error("Error to convert response to JSON", e);
        }
    }
}
