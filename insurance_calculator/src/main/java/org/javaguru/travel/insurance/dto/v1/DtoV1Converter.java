package org.javaguru.travel.insurance.dto.v1;

import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.dto.RiskPremium;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DtoV1Converter {

    public TravelCalculatePremiumCoreCommand buildCoreCommand (TravelCalculatePremiumRequestV1 request) {
        return new TravelCalculatePremiumCoreCommand(buildAgreement(request));
    }

    public TravelCalculatePremiumResponseV1 buildResponse (TravelCalculatePremiumCoreResult result) {
        return result.hasErrors()
                ? buildResponseWithErrors(result)
                : buildSuccessfulResponse(result);
    }

    private AgreementDTO buildAgreement (TravelCalculatePremiumRequestV1 request) {
        PersonDTO person = new PersonDTO(
                request.getPersonFirstName(),
                request.getPersonLastName(),
                request.getPersonCode(),
                request.getPersonBirthDate(),
                request.getMedicalRiskLimitLevel()
        );
        return new AgreementDTO(
                request.getAgreementDateFrom(),
                request.getAgreementDateTo(),
                request.getCountry(),
                request.getSelectedRisks(),
                List.of(person)
        );
    }

    private TravelCalculatePremiumResponseV1 buildResponseWithErrors(TravelCalculatePremiumCoreResult result) {
        List<ValidationError> errors = transformValidationErrorsToV1(result.errors());
        return new TravelCalculatePremiumResponseV1(errors);
    }

    private List<ValidationError> transformValidationErrorsToV1(List<ValidationErrorDTO> errors) {
        return errors.stream()
                .map(error -> new ValidationError(error.errorCode(), error.description()))
                .toList();
    }

    private TravelCalculatePremiumResponseV1 buildSuccessfulResponse(TravelCalculatePremiumCoreResult result) {
        AgreementDTO agreement = result.agreement();
        PersonDTO person = agreement.persons().getFirst();
        List<RiskPremium> riskPremiums = transformRiskPremiumsToV1(person.risks());
        return new TravelCalculatePremiumResponseV1(
                agreement.uuid().toString(),
                person.personFirstName(),
                person.personLastName(),
                person.personCode(),
                person.personBirthDate(),
                agreement.agreementDateFrom(),
                agreement.agreementDateTo(),
                agreement.country(),
                person.medicalRiskLimitLevel(),
                agreement.agreementPremium(),
                riskPremiums
        );
    }

    private List<RiskPremium> transformRiskPremiumsToV1(List<RiskDTO> risks) {
        return risks.stream()
                .map(risk -> new RiskPremium(risk.riskIc(), risk.premium()))
                .toList();
    }

}
