package org.javaguru.travel.insurance.dto.internal;

import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreResult;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.dto.RiskPremium;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.javaguru.travel.insurance.dto.v2.PersonResponseDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class GetAgreementDTOConverter {

    public TravelGetAgreementCoreCommand buildCommand(String uuid) {
        return new TravelGetAgreementCoreCommand(uuid);
    }

    public TravelGetAgreementResponse buildResponse(TravelGetAgreementCoreResult result) {
        return result.hasErrors()
                ? buildResponseWithErrors(result)
                : buildSuccessfulResponse(result);
    }

    private TravelGetAgreementResponse buildResponseWithErrors(TravelGetAgreementCoreResult result) {
        List<ValidationError> errors = transformValidationErrors(result.errors());
        return new TravelGetAgreementResponse(errors);
    }

    private List<ValidationError> transformValidationErrors(List<ValidationErrorDTO> errors) {
        return errors.stream()
                .map(error -> new ValidationError(error.errorCode(), error.description()))
                .toList();
    }

    private TravelGetAgreementResponse buildSuccessfulResponse(TravelGetAgreementCoreResult result) {
        AgreementDTO agreement = result.agreement();
        List<PersonResponseDTO> persons = transformPersons(agreement.persons());
        return new TravelGetAgreementResponse(
                agreement.uuid(),
                agreement.agreementDateFrom(),
                agreement.agreementDateTo(),
                agreement.country(),
                agreement.agreementPremium(),
                persons
        );
    }

    private List<PersonResponseDTO> transformPersons(List<PersonDTO> persons) {
        return persons.stream()
                .map(person -> new PersonResponseDTO(
                        person.personFirstName(),
                        person.personLastName(),
                        person.personCode(),
                        person.personBirthDate(),
                        person.medicalRiskLimitLevel(),
                        calculatePremiumByRisks(person.risks()),
                        transformRiskPremiumsToV2(person.risks())
                ))
                .toList();
    }

    private List<RiskPremium> transformRiskPremiumsToV2(List<RiskDTO> risks) {
        return risks.stream()
                .map(risk -> new RiskPremium(risk.riskIc(), risk.premium()))
                .toList();
    }

    private BigDecimal calculatePremiumByRisks(List<RiskDTO> risks) {
        return risks.stream()
                .map(RiskDTO::premium)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
