package org.javaguru.travel.insurance.rest.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class TravelGetAgreementRequestLogger {
    private static final Logger logger = LoggerFactory.getLogger(TravelGetAgreementRequestLogger.class);

    void log(String request) {
            logger.info("REQUEST: {uuid: {}}", request);
    }
}
