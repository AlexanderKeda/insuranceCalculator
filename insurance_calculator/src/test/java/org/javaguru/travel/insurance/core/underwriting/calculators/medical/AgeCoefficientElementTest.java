package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.repositories.AgeCoefficientRepository;
import org.javaguru.travel.insurance.core.util.AgeCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgeCoefficientElementTest {

    @Mock
    private AgeCalculator ageCalculatorMock;

    @Mock
    private AgeCoefficientRepository ageCoefficientRepositoryMock;

    @Mock
    private AgreementDTO agreementMock;

    @Mock
    private PersonDTO personMock;

    @Test
    void shouldReturnCorrectAgeCoefficient() {
        var ageCoefficientElement = new AgeCoefficientElement(
                true,
                ageCalculatorMock,
                ageCoefficientRepositoryMock
        );
        BigDecimal expectedCoefficient = new BigDecimal("1.3");
        when(ageCalculatorMock.calculate(personMock.personBirthDate()))
                .thenReturn(25);
        when(ageCoefficientRepositoryMock.findByAge(25))
                .thenReturn(Optional.of(new org.javaguru.travel.insurance.core.domain.AgeCoefficient(1L,20,40, expectedCoefficient)));
        assertEquals(expectedCoefficient, ageCoefficientElement.calculate(agreementMock, personMock));
    }

    @Test
    void shouldThrowExceptionWhenCoefficientNotFoundInDB() {
        var ageCoefficientElement = new AgeCoefficientElement(
                true,
                ageCalculatorMock,
                ageCoefficientRepositoryMock
        );
        when(ageCalculatorMock.calculate(personMock.personBirthDate()))
                .thenReturn(25);
        when(ageCoefficientRepositoryMock.findByAge(25))
                .thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> ageCoefficientElement.calculate(agreementMock, personMock));
    }

    @Test
    void shouldReturnDefaultCoefficientWhenMedicalRiskAgeCoefficientIsDisabled() {
        var ageCoefficientElement = new AgeCoefficientElement(
                false,
                ageCalculatorMock,
                ageCoefficientRepositoryMock
        );
        assertEquals(BigDecimal.ONE, ageCoefficientElement.calculate(agreementMock, personMock));
        Mockito.verifyNoInteractions(personMock);
        Mockito.verifyNoInteractions(ageCalculatorMock);
        Mockito.verifyNoInteractions(ageCoefficientRepositoryMock);
    }

}