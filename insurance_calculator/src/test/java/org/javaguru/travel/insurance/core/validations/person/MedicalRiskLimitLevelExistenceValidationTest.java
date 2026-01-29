package org.javaguru.travel.insurance.core.validations.person;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.repositories.MedicalRiskLimitLevelRepository;
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
class MedicalRiskLimitLevelExistenceValidationTest {

    @Mock
    private ClassifierValueRepository classifierValueRepositoryMock;

    @Mock
    private MedicalRiskLimitLevelRepository medicalRiskLimitLevelRepository;

    @Mock
    private ValidationErrorFactory errorFactoryMock;

    @InjectMocks
    private MedicalRiskLimitLevelExistenceValidation limitLevelExistenceValidation;

    @Mock
    private PersonDTO personMock;

    @Mock
    private AgreementDTO agreementMock;

    @Test
    void shouldSucceedWhenLimitLevelExist() {
        when(personMock.medicalRiskLimitLevel())
                .thenReturn("LIMIT_LEVEL");
        when(classifierValueRepositoryMock
                .existsByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", "LIMIT_LEVEL"))
                .thenReturn(true);
        when(medicalRiskLimitLevelRepository
                .existsByMedicalRiskLimitLevelIc("LIMIT_LEVEL"))
                .thenReturn(true);
        assertEquals(Optional.empty(), limitLevelExistenceValidation.validate(agreementMock, personMock));
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldSucceedWhenLimitLevelIsNull() {
        when(personMock.medicalRiskLimitLevel())
                .thenReturn(null);
        assertEquals(Optional.empty(), limitLevelExistenceValidation.validate(agreementMock, personMock));
        Mockito.verifyNoInteractions(classifierValueRepositoryMock);
        Mockito.verifyNoInteractions(medicalRiskLimitLevelRepository);
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldSucceedWhenLimitLevelIsEmpty() {
        when(personMock.medicalRiskLimitLevel())
                .thenReturn("");
        assertEquals(Optional.empty(), limitLevelExistenceValidation.validate(agreementMock, personMock));
        Mockito.verifyNoInteractions(classifierValueRepositoryMock);
        Mockito.verifyNoInteractions(medicalRiskLimitLevelRepository);
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldReturnErrorWhenLimitLevelIcIsNotExist() {
        String limitLevel = "FAKE_LIMIT_LEVEL";
        var placeholder = new Placeholder("NOT_EXISTING_RISK_LEVEL", limitLevel);
        when(personMock.medicalRiskLimitLevel())
                .thenReturn(limitLevel);
        when(classifierValueRepositoryMock
                .existsByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", limitLevel))
                .thenReturn(false);
        when(errorFactoryMock.buildError(
                "ERROR_CODE_16",
                List.of(placeholder)
        )).thenReturn(new ValidationErrorDTO("", ""));
        var errorOpt = limitLevelExistenceValidation.validate(agreementMock, personMock);
        assertTrue(errorOpt.isPresent());
        assertEquals(new ValidationErrorDTO("", ""), errorOpt.get());
        Mockito.verifyNoInteractions(medicalRiskLimitLevelRepository);
    }

    @Test
    void shouldReturnErrorWhenLimitLevelCoefficientIsNotExistAndHasRequiredRisk() {
        String limitLevel = "LIMIT_LEVEL";
        var placeholder = new Placeholder("NOT_EXISTING_RISK_LEVEL", limitLevel);
        when(personMock.medicalRiskLimitLevel())
                .thenReturn(limitLevel);
        when(classifierValueRepositoryMock
                .existsByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", limitLevel))
                .thenReturn(true);
        when(medicalRiskLimitLevelRepository
                .existsByMedicalRiskLimitLevelIc(limitLevel))
                .thenReturn(false);
        when(errorFactoryMock.buildError(
                "ERROR_CODE_16",
                List.of(placeholder)
        )).thenReturn(new ValidationErrorDTO("", ""));
        var errorOpt = limitLevelExistenceValidation.validate(agreementMock, personMock);
        assertTrue(errorOpt.isPresent());
        assertEquals(new ValidationErrorDTO("", ""), errorOpt.get());
    }

}