package org.javaguru.travel.insurance.core.validations.agreement;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.repositories.CountryDefaultDayRateRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryExistenceValidationTest {

    @Mock
    private ValidationErrorFactory errorFactoryMock;

    @Mock
    private ClassifierValueRepository classifierValueRepositoryMock;

    @Mock
    private CountryDefaultDayRateRepository countryDefaultDayRateRepository;

    @InjectMocks
    private CountryExistenceValidation countryExistenceValidation;

    @Mock
    private AgreementDTO agreementMock;

    @Test
    void shouldSucceedWhenCountryExistAndHasRequiredRisks() {
        when(agreementMock.country())
                .thenReturn("LATVIA");
        when(classifierValueRepositoryMock.existsByClassifierTitleAndIc("COUNTRY", "LATVIA"))
                .thenReturn(true);
        when(countryDefaultDayRateRepository.existsByCountryIc("LATVIA"))
                .thenReturn(true);
        assertEquals(Optional.empty(), countryExistenceValidation.validate(agreementMock));
    }

    @Test
    void shouldSucceedWhenCountryIsNull() {
        when(agreementMock.country())
                .thenReturn(null);
        assertEquals(Optional.empty(), countryExistenceValidation.validate(agreementMock));
        Mockito.verifyNoInteractions(classifierValueRepositoryMock);
    }

    @Test
    void shouldSucceedWhenCountryIsEmpty() {
        when(agreementMock.country())
                .thenReturn("");
        assertEquals(Optional.empty(), countryExistenceValidation.validate(agreementMock));
        Mockito.verifyNoInteractions(classifierValueRepositoryMock);
    }

    @Test
    void shouldReturnErrorWhenCountryIsNotExist() {
        String countryName = "FAKE_COUNTRY";
        Placeholder correctPlaceholder = new Placeholder("NOT_EXISTING_COUNTRY", countryName);
        when(agreementMock.country())
                .thenReturn(countryName);
        when(classifierValueRepositoryMock.existsByClassifierTitleAndIc("COUNTRY", countryName))
                .thenReturn(false);
        when(errorFactoryMock.buildError(
                "ERROR_CODE_11",
                List.of(correctPlaceholder)
        )).thenReturn(new ValidationErrorDTO("", ""));
        var errorOpt = countryExistenceValidation.validate(agreementMock);
        assertTrue(errorOpt.isPresent());
        assertEquals(new ValidationErrorDTO("", ""), errorOpt.get());
    }

    @Test
    void shouldReturnErrorWhenDefaultDayRateIsNotExist() {
        String countryName = "FAKE_COUNTRY";
        Placeholder correctPlaceholder = new Placeholder("NOT_EXISTING_COUNTRY", countryName);
        when(agreementMock.country())
                .thenReturn(countryName);
        when(classifierValueRepositoryMock.existsByClassifierTitleAndIc("COUNTRY", countryName))
                .thenReturn(true);
        when(countryDefaultDayRateRepository.existsByCountryIc(countryName))
                .thenReturn(false);
        when(errorFactoryMock.buildError(
                "ERROR_CODE_11",
                List.of(correctPlaceholder)
        )).thenReturn(new ValidationErrorDTO("", ""));
        var errorOpt = countryExistenceValidation.validate(agreementMock);
        assertTrue(errorOpt.isPresent());
        assertEquals(new ValidationErrorDTO("", ""), errorOpt.get());
    }

}