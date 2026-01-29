package org.javaguru.travel.insurance.dto.v2;

import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.dto.RiskPremium;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DtoV2Converter {

    public TravelCalculatePremiumCoreCommand buildCoreCommand(TravelCalculatePremiumRequestV2 request) {
        return new TravelCalculatePremiumCoreCommand(buildAgreement(request));
    }

    public TravelCalculatePremiumResponseV2 buildResponse(TravelCalculatePremiumCoreResult result) {
        return result.hasErrors()
                ? buildResponseWithErrors(result)
                : buildSuccessfulResponse(result);
    }

    private AgreementDTO buildAgreement(TravelCalculatePremiumRequestV2 request) {
        List<PersonDTO> persons = transformPersonRequestV2ToPersonDtoList(request);
        return new AgreementDTO(
                request.getAgreementDateFrom(),
                request.getAgreementDateTo(),
                request.getCountry(),
                request.getSelectedRisks(),
                persons
        );
    }

    private List<PersonDTO> transformPersonRequestV2ToPersonDtoList(TravelCalculatePremiumRequestV2 request) {
        var persons = request.getPersons();
        return persons == null
                ? null
                : persons.stream()
                .map(person -> new PersonDTO(person.getPersonFirstName(),
                        person.getPersonLastName(),
                        person.getPersonCode(),
                        person.getPersonBirthDate(),
                        person.getMedicalRiskLimitLevel()
                ))
                .toList();
    }

    private TravelCalculatePremiumResponseV2 buildResponseWithErrors(TravelCalculatePremiumCoreResult result) {
        List<ValidationError> errors = transformValidationErrorsToV2(result.errors());
        return new TravelCalculatePremiumResponseV2(errors);
    }

    private List<ValidationError> transformValidationErrorsToV2(List<ValidationErrorDTO> errors) {
        return errors.stream()
                .map(error -> new ValidationError(error.errorCode(), error.description()))
                .toList();
    }

    private TravelCalculatePremiumResponseV2 buildSuccessfulResponse(TravelCalculatePremiumCoreResult result) {
        AgreementDTO agreement = result.agreement();
        List<PersonResponseDTO> persons = transformPersonsToV2(agreement.persons());
        return new TravelCalculatePremiumResponseV2(
                agreement.uuid().toString(),
                agreement.agreementDateFrom(),
                agreement.agreementDateTo(),
                agreement.country(),
                agreement.agreementPremium(),
                persons
        );
    }

    private List<PersonResponseDTO> transformPersonsToV2(List<PersonDTO> persons) {
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
