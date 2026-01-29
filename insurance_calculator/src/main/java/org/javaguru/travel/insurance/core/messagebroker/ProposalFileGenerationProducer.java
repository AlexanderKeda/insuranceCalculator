package org.javaguru.travel.insurance.core.messagebroker;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;

public interface ProposalFileGenerationProducer {

    void send(AgreementDTO agreement);

}
