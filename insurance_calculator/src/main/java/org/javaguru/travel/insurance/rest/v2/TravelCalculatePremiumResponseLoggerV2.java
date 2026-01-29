package org.javaguru.travel.insurance.rest.v2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.javaguru.travel.insurance.dto.v2.TravelCalculatePremiumResponseV2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TravelCalculatePremiumResponseLoggerV2 {
    private static final Logger logger = LoggerFactory.getLogger(TravelCalculatePremiumResponseLoggerV2.class);
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    void log(TravelCalculatePremiumResponseV2 response) {
        try {
            String json = objectMapper.writeValueAsString(response);
            logger.info("RESPONSE: {}", json);
        } catch (JsonProcessingException e) {
            logger.error("Error to convert response to JSON", e);
        }
    }
}
