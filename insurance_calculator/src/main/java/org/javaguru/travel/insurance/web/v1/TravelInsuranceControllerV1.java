package org.javaguru.travel.insurance.web.v1;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import org.javaguru.travel.insurance.core.services.travel.calculate.TravelCalculatePremiumService;
import org.javaguru.travel.insurance.dto.v1.DtoV1Converter;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumResponseV1;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TravelInsuranceControllerV1 {

    private final TravelCalculatePremiumService service;
    private final DtoV1Converter dtoV1Converter;

    @GetMapping("/insurance/travel/web/v1")
    public String showForm(ModelMap modelMap) {
        modelMap.addAttribute("request", new TravelCalculatePremiumRequestV1());
        modelMap.addAttribute("response", new TravelCalculatePremiumResponseV1());
        return "travel-calculate-premium-v1";
    }

    @PostMapping("/insurance/travel/web/v1")
    public String processForm(@ModelAttribute(value = "request") TravelCalculatePremiumRequestV1 request,
                              ModelMap modelMap) {
        TravelCalculatePremiumCoreCommand command = dtoV1Converter.buildCoreCommand(request);
        TravelCalculatePremiumCoreResult result = service.calculatePremium(command);
        TravelCalculatePremiumResponseV1 response = dtoV1Converter.buildResponse(result);
        modelMap.addAttribute("response", response);
        return "travel-calculate-premium-v1";
    }
}
