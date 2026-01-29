package org.javaguru.travel.insurance.core.services.travel.calculate;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
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
class AgreementPremiumCalculatorTest {

    @Mock
    private AgreementPersonsPremiumCalculator personsPremiumCalculatorMock;

    @InjectMocks
    private AgreementPremiumCalculator agreementPremiumCalculator;

    @Test
    void shouldReturnDateFrom() {
        var agreement = getCorrectAgreement();
        when(personsPremiumCalculatorMock.calculate(agreement))
                .thenReturn(List.of());
        var resultAgreement = agreementPremiumCalculator.calculateAgreementPremiums(agreement);
        assertEquals(agreement.agreementDateFrom(), resultAgreement.agreementDateFrom());
    }

    @Test
    void shouldReturnDateTo() {
        var agreement = getCorrectAgreement();
        when(personsPremiumCalculatorMock.calculate(agreement))
                .thenReturn(List.of());
        var resultAgreement = agreementPremiumCalculator.calculateAgreementPremiums(agreement);
        assertEquals(agreement.agreementDateTo(), resultAgreement.agreementDateTo());
    }

    @Test
    void shouldReturnCountry() {
        var agreement = getCorrectAgreement();
        when(personsPremiumCalculatorMock.calculate(agreement))
                .thenReturn(List.of());
        var resultAgreement = agreementPremiumCalculator.calculateAgreementPremiums(agreement);
        assertEquals(agreement.country(), resultAgreement.country());
    }

    @Test
    void shouldReturnCorrectSelectedRisks() {
        var agreement = getCorrectAgreement();
        when(personsPremiumCalculatorMock.calculate(agreement))
                .thenReturn(List.of());
        var resultAgreement = agreementPremiumCalculator.calculateAgreementPremiums(agreement);
        assertEquals(agreement.selectedRisks(), resultAgreement.selectedRisks());
    }

    @Test
    void shouldReturnCorrectPersons() {
        var agreement = getCorrectAgreement();
        var person1 = new PersonDTO("name1",
                "name1", "code" ,
                "LimitLevel",
                List.of(),
                LocalDate.now());
        var person2 = new PersonDTO("name1",
                "name1", "code",
                "LimitLevel",
                List.of(),
                LocalDate.now());
        when(personsPremiumCalculatorMock.calculate(agreement))
                .thenReturn(List.of(person1, person2));
        var resultAgreement = agreementPremiumCalculator.calculateAgreementPremiums(agreement);
        assertEquals(List.of(person1, person2), resultAgreement.persons());
    }

    @Test
    void shouldReturnCorrectAgreementPremium() {
        var agreement = getCorrectAgreement();
        var premium1 = new BigDecimal("4");
        var premium2 = new BigDecimal("2.5");
        var premium3 = new BigDecimal("5.7");
        var risk1 = new RiskDTO("ic1", premium1);
        var risk2 = new RiskDTO("ic2", premium2);
        var risk3 = new RiskDTO("ic1", premium3);
        var person1 = new PersonDTO("name1",
                "name1", "code",
                "LimitLevel",
                List.of(risk1, risk2),
                LocalDate.now());
        var person2 = new PersonDTO("name1",
                "name1", "code",
                "LimitLevel",
                List.of(risk3),
                LocalDate.now());
        when(personsPremiumCalculatorMock.calculate(agreement))
                .thenReturn(List.of(person1, person2));
        var resultAgreement = agreementPremiumCalculator.calculateAgreementPremiums(agreement);
        var agreementPremium = premium1.add(premium2).add(premium3);
        assertEquals(agreementPremium, resultAgreement.agreementPremium());
    }

    private AgreementDTO getCorrectAgreement() {
        LocalDate dateFrom = LocalDate.now();
        LocalDate dateTo = LocalDate.now().plusDays(5);
        String country = "Latvia";
        List<String> selectedRisks = List.of("risk1", "risk2");
        List<PersonDTO> persons = List.of();
        return new AgreementDTO(dateFrom,
                dateTo,
                country,
                selectedRisks,
                persons);
    }

}