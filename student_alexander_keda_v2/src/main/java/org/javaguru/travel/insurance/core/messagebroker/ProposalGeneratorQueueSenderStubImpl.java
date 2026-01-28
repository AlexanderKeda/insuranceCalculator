package org.javaguru.travel.insurance.core.messagebroker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"h2", "mysql-local", "mysql-container"})
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
class ProposalGeneratorQueueSenderStubImpl implements ProposalGeneratorQueueSender {

    private final ObjectMapper objectMapper;

    @Override
    public void send(AgreementDTO agreement) {

        try {
            String json = objectMapper.writeValueAsString(agreement);
            log.info(json);
        } catch (JsonProcessingException e) {
            log.error("Error to convert agreement to JSON", e);
        } catch (Exception e) {
            log.error("Error to sent proposal generation message", e);
        }

    }
}
