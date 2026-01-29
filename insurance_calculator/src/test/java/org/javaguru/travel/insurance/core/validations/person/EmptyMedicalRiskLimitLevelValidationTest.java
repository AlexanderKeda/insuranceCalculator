package org.javaguru.travel.insurance.core.validations.person;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmptyMedicalRiskLimitLevelValidationTest {

    @Mock
    private ValidationErrorFactory errorFactoryMock;

    @Mock
    private PersonDTO personMock;

    @Mock
    private AgreementDTO agreementMock;

    @Test
    void shouldSucceedWhenLimitLevelDisabled() {
        var emptyLimitLevelValidation = new EmptyMedicalRiskLimitLevelValidation(
                false,
                errorFactoryMock
        );
        assertEquals(Optional.empty(), emptyLimitLevelValidation.validate(agreementMock, personMock));
        Mockito.verifyNoInteractions(errorFactoryMock);
        Mockito.verifyNoInteractions(agreementMock);
    }

    @Test
    void shouldSucceedWhenLimitLevelIsNotEmptyAndHasRequiredRisks() {
        var emptyLimitLevelValidation = new EmptyMedicalRiskLimitLevelValidation(
                true,
                errorFactoryMock
        );
        when(agreementMock.selectedRisks())
                .thenReturn(List.of("TRAVEL_MEDICAL"));
        when(personMock.medicalRiskLimitLevel())
                .thenReturn("LIMIT_LEVEL");
        assertEquals(Optional.empty(), emptyLimitLevelValidation.validate(agreementMock, personMock));
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldSucceedWhenRequiredRisksAreMissing() {
        var emptyLimitLevelValidation = new EmptyMedicalRiskLimitLevelValidation(
                true,
                errorFactoryMock
        );
        when(agreementMock.selectedRisks())
                .thenReturn(List.of("FAKE_RISK"));
        assertEquals(Optional.empty(), emptyLimitLevelValidation.validate(agreementMock, personMock));
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldSucceedWhenRequiredRisksAreNull() {
        var emptyLimitLevelValidation = new EmptyMedicalRiskLimitLevelValidation(
                true,
                errorFactoryMock
        );
        when(agreementMock.selectedRisks())
                .thenReturn(null);
        assertEquals(Optional.empty(), emptyLimitLevelValidation.validate(agreementMock, personMock));
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldReturnErrorWhenLimitLevelIsEmptyAndHasRequiredRisks() {
        var emptyLimitLevelValidation = new EmptyMedicalRiskLimitLevelValidation(
                true,
                errorFactoryMock
        );
        when(agreementMock.selectedRisks())
                .thenReturn(List.of("TRAVEL_MEDICAL"));
        when(personMock.medicalRiskLimitLevel())
                .thenReturn("");
        when(errorFactoryMock.buildError("ERROR_CODE_15"))
                .thenReturn(new ValidationErrorDTO("", ""));
        var errorOpt = emptyLimitLevelValidation.validate(agreementMock, personMock);
        assertTrue(errorOpt.isPresent());
        assertEquals(new ValidationErrorDTO("", ""), errorOpt.get());
    }

    @Test
    void shouldReturnErrorWhenLimitLevelIsNullAndHasRequiredRisks() {
        var emptyLimitLevelValidation = new EmptyMedicalRiskLimitLevelValidation(
                true,
                errorFactoryMock
        );
        when(agreementMock.selectedRisks())
                .thenReturn(List.of("TRAVEL_MEDICAL"));
        when(personMock.medicalRiskLimitLevel())
                .thenReturn(null);
        when(errorFactoryMock.buildError("ERROR_CODE_15"))
                .thenReturn(new ValidationErrorDTO("", ""));
        var errorOpt = emptyLimitLevelValidation.validate(agreementMock, personMock);
        assertTrue(errorOpt.isPresent());
        assertEquals(new ValidationErrorDTO("", ""), errorOpt.get());
    }

}