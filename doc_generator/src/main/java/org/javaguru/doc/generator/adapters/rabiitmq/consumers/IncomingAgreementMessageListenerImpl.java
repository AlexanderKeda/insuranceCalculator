package org.javaguru.doc.generator.adapters.rabiitmq.consumers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.doc.generator.core.dto.AgreementDTO;
import org.javaguru.doc.generator.core.services.generate.pdf.PdfGenerator;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class IncomingAgreementMessageListenerImpl implements IncomingAgreementMessageListener {

    private final PdfGenerator pdfGenerator;

    @Override
    @RabbitListener(queues = "${contracts.rabbitmq.queue}", ackMode = "AUTO")
    public void receive(AgreementDTO agreement) {
        try{
            log.info("Agreement received: {}", agreement.uuid());
            pdfGenerator.generate(agreement);
            log.info("Succeed with create agreement-proposal-{}.pdf", agreement.uuid());
        } catch (IOException e) {
            throw new RecoverableDataAccessException("Failure in creating proposal", e);
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException(e);
        }

    }
}