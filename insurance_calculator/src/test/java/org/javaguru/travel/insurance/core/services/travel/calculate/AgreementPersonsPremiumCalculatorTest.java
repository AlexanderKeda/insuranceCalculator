package org.javaguru.travel.insurance.core.services.travel.calculate;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.javaguru.travel.insurance.core.underwriting.TravelPremiumCalculationResult;
import org.javaguru.travel.insurance.core.underwriting.TravelPremiumUnderwriting;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgreementPersonsPremiumCalculatorTest {

    @Mock
    private TravelPremiumUnderwriting underwritingMock;

    @InjectMocks
    private AgreementPersonsPremiumCalculator personsPremiumCalculator;

    @Mock
    private AgreementDTO agreementMock;

    @Test
    void shouldReturnCorrectFirstName() {
        var person = getCorrectPersonDTO();
        var risks = List.of(new RiskDTO("ic", BigDecimal.ZERO));
        when(agreementMock.persons())
                .thenReturn(List.of(person));
        when(underwritingMock.calculatePremium(agreementMock, person))
                .thenReturn(new TravelPremiumCalculationResult(BigDecimal.ZERO, risks));
        var updatedPersons = personsPremiumCalculator.calculate(agreementMock);
        assertEquals(person.personFirstName(),
                updatedPersons.getFirst().personFirstName());
    }

    @Test
    void shouldReturnCorrectLastName() {
        var person = getCorrectPersonDTO();
        var risks = List.of(new RiskDTO("ic", BigDecimal.ZERO));
        when(agreementMock.persons())
                .thenReturn(List.of(person));
        when(underwritingMock.calculatePremium(agreementMock, person))
                .thenReturn(new TravelPremiumCalculationResult(BigDecimal.ZERO, risks));
        var updatedPersons = personsPremiumCalculator.calculate(agreementMock);
        assertEquals(person.personLastName(),
                updatedPersons.getFirst().personLastName());
    }

    @Test
    void shouldReturnCorrectMedicalRiskLimitLevel() {
        var person = getCorrectPersonDTO();
        var risks = List.of(new RiskDTO("ic", BigDecimal.ZERO));
        when(agreementMock.persons())
                .thenReturn(List.of(person));
        when(underwritingMock.calculatePremium(agreementMock, person))
                .thenReturn(new TravelPremiumCalculationResult(BigDecimal.ZERO, risks));
        var updatedPersons = personsPremiumCalculator.calculate(agreementMock);
        assertEquals(person.medicalRiskLimitLevel(),
                updatedPersons.getFirst().medicalRiskLimitLevel());
    }

    @Test
    void shouldReturnCorrectBirthDate() {
        var person = getCorrectPersonDTO();
        var risks = List.of(new RiskDTO("ic", BigDecimal.ZERO));
        when(agreementMock.persons())
                .thenReturn(List.of(person));
        when(underwritingMock.calculatePremium(agreementMock, person))
                .thenReturn(new TravelPremiumCalculationResult(BigDecimal.ZERO, risks));
        var updatedPersons = personsPremiumCalculator.calculate(agreementMock);
        assertEquals(person.personBirthDate(),
                updatedPersons.getFirst().personBirthDate());
    }

    @Test
    void shouldReturnCorrectRisks() {
        var person = getCorrectPersonDTO();
        var risks = List.of(new RiskDTO("ic", BigDecimal.ZERO));
        when(agreementMock.persons())
                .thenReturn(List.of(person));
        when(underwritingMock.calculatePremium(agreementMock, person))
                .thenReturn(new TravelPremiumCalculationResult(BigDecimal.ZERO, risks));
        var updatedPersons = personsPremiumCalculator.calculate(agreementMock);
        assertEquals(risks,
                updatedPersons.getFirst().risks());
    }

    private PersonDTO getCorrectPersonDTO() {
        return new PersonDTO(
                "first",
                "last",
                "code",
                LocalDate.now(),
                "LimitLevel"
        );
    }



}