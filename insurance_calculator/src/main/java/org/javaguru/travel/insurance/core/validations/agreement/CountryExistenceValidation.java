package org.javaguru.travel.insurance.core.validations.agreement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.repositories.CountryDefaultDayRateRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CountryExistenceValidation implements TravelAgreementFieldsValidation {

    private final ValidationErrorFactory validationErrorFactory;
    private final ClassifierValueRepository classifierValueRepository;
    private final CountryDefaultDayRateRepository countryDefaultDayRateRepository;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement) {
        return agreement.country() != null
                && !agreement.country().isBlank()
                ? validateCountryExistence(agreement)
                : Optional.empty();
    }

    private Optional<ValidationErrorDTO> validateCountryExistence(AgreementDTO agreement) {
        return doesCountryExist(agreement.country())
                && doesDefaultDayRateExist(agreement.country())
                ? Optional.empty()
                : Optional.of(buildCountryNotFoundError(agreement.country()));
    }

    private boolean doesCountryExist(String countryIc) {
        return classifierValueRepository
                .existsByClassifierTitleAndIc("COUNTRY", countryIc);
    }

    private boolean doesDefaultDayRateExist(String countryIc) {
        return countryDefaultDayRateRepository
                .existsByCountryIc(countryIc);
    }

    private ValidationErrorDTO buildCountryNotFoundError(String countryIc) {
        return validationErrorFactory
                .buildError(
                        "ERROR_CODE_11",
                        List.of(new Placeholder("NOT_EXISTING_COUNTRY", countryIc))
                );
    }
}
