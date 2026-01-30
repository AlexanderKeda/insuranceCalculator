package org.javaguru.doc.generator.adapters.rabiitmq.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.doc.generator.core.dto.AgreementDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class IncomingAgreementMessageListenerImpl implements IncomingAgreementMessageListener {

    private final ObjectMapper objectMapper;

    @Override
    @RabbitListener(queues = "${contracts.rabbitmq.queue}", ackMode = "AUTO")
    public void receive(AgreementDTO agreement) {
        try{
            String json = objectMapper.writeValueAsString(agreement);
            log.info("Agreement received: {}", json);
        } catch (Exception e) {
            log.error("Error to convert agreement to JSON", e);
        }

    }
}