package org.javaguru.travel.insurance.core.messagebroker;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.travel.insurance.config.RabbitMQConfig;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("mysql-container")
@RequiredArgsConstructor(access = AccessLevel.MODULE)
@Slf4j
class ProposalFileGenerationProducerImpl implements ProposalFileGenerationProducer {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void send(AgreementDTO agreement) {
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, agreement);
            log.info("Send the agreement with UUID {} to the queue", agreement.uuid());
    }
}
