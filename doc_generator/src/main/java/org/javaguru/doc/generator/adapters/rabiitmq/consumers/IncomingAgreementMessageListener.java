package org.javaguru.doc.generator.adapters.rabiitmq.consumers;

import org.javaguru.doc.generator.core.dto.AgreementDTO;

public interface IncomingAgreementMessageListener {

    void receive(AgreementDTO agreement);

}
