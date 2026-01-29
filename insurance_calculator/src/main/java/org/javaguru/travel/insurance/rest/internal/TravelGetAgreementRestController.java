package org.javaguru.travel.insurance.rest.internal;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.services.travel.get.agreement.TravelGetAgreementService;
import org.javaguru.travel.insurance.dto.internal.GetAgreementDTOConverter;
import org.javaguru.travel.insurance.dto.internal.TravelGetAgreementResponse;
import org.javaguru.travel.insurance.rest.common.TravelRestRequestExecutionTimeLogger;
import com.google.common.base.Stopwatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insurance/travel/api/internal")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TravelGetAgreementRestController {

    private final TravelGetAgreementRequestLogger requestLogger;
    private final TravelGetAgreementResponseLogger responseLogger;
    private final TravelRestRequestExecutionTimeLogger requestExecutionTimeLogger;
    private final GetAgreementDTOConverter getAgreementDTOConverter;
    private final TravelGetAgreementService travelGetAgreementService;

    @GetMapping(path = "/agreement/{uuid}",
            produces = "application/json")
    public TravelGetAgreementResponse getAgreement(@PathVariable("uuid") String uuid) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        var response = processGetAgreementRequest(uuid);
        requestExecutionTimeLogger.log(stopwatch);
        return response;
    }

    private TravelGetAgreementResponse processGetAgreementRequest(String uuid) {
        requestLogger.log(uuid);
        var command = getAgreementDTOConverter.buildCommand(uuid);
        var coreResult = travelGetAgreementService.getAgreement(command);
        var response = getAgreementDTOConverter.buildResponse(coreResult);
        responseLogger.log(response);
        return response;
    }
}
