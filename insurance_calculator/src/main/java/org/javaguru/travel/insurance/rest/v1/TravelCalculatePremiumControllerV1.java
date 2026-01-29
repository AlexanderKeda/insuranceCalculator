package org.javaguru.travel.insurance.rest.v1;

import com.google.common.base.Stopwatch;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import org.javaguru.travel.insurance.core.services.travel.calculate.TravelCalculatePremiumService;
import org.javaguru.travel.insurance.dto.v1.DtoV1Converter;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumResponseV1;
import org.javaguru.travel.insurance.rest.common.TravelRestRequestExecutionTimeLogger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insurance/travel/api/v1")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TravelCalculatePremiumControllerV1 {

	private final TravelCalculatePremiumService calculatePremiumService;
	private final DtoV1Converter dtoV1Converter;
	private final TravelCalculatePremiumRequestLoggerV1 requestLogger;
	private final TravelCalculatePremiumResponseLoggerV1 responseLogger;
	private final TravelRestRequestExecutionTimeLogger requestExecutionTimeLogger;

	@PostMapping(path = "/",
			consumes = "application/json",
			produces = "application/json")
	public TravelCalculatePremiumResponseV1 calculatePremium(@RequestBody TravelCalculatePremiumRequestV1 request) {
		Stopwatch stopwatch = Stopwatch.createStarted();
		TravelCalculatePremiumResponseV1 response = processRequest(request);
		requestExecutionTimeLogger.log(stopwatch);
		return response;
	}

	private TravelCalculatePremiumResponseV1 processRequest(TravelCalculatePremiumRequestV1 request) {
		requestLogger.log(request);
		TravelCalculatePremiumCoreCommand command = dtoV1Converter.buildCoreCommand(request);
		TravelCalculatePremiumCoreResult result = calculatePremiumService.calculatePremium(command);
		TravelCalculatePremiumResponseV1 response = dtoV1Converter.buildResponse(result);
		responseLogger.log(response);
		return response;
	}

}