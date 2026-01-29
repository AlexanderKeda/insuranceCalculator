package org.javaguru.travel.insurance.core.validations.agreement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SelectedRisksExistenceValidation implements TravelAgreementFieldsValidation {

    private final ValidationErrorFactory validationErrorFactory;
    private final ClassifierValueRepository classifierValueRepository;

    @Override
    public List<ValidationErrorDTO> validateList(AgreementDTO agreement) {
        return agreement.selectedRisks() != null
                ? validateSelectedRisksExistence(agreement)
                : List.of();
    }

    private List<ValidationErrorDTO> validateSelectedRisksExistence(AgreementDTO agreement) {
        return agreement.selectedRisks().stream()
                .filter(Predicate.not(this::doesRiskExist))
                .map(this::buildRiskNotFoundError)
                .toList();
    }

    private boolean doesRiskExist(String riskIc) {
        return classifierValueRepository.existsByClassifierTitleAndIc("RISK_TYPE", riskIc);
    }

    private ValidationErrorDTO buildRiskNotFoundError(String riskIc) {
        return validationErrorFactory
                .buildError(
                        "ERROR_CODE_9",
                        List.of(new Placeholder("NOT_EXISTING_RISK", riskIc))
                );
    }
}
