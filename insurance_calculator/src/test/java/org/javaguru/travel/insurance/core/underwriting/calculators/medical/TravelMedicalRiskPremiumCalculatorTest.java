package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelMedicalRiskPremiumCalculatorTest {

    @Mock
    private MedicalRiskElement elementCalculatorMock1;

    @Mock
    private MedicalRiskElement elementCalculatorMock2;

    private TravelMedicalRiskPremiumCalculator travelMedicalRiskPremiumCalculator;

    @Mock
    private AgreementDTO agreementMock;

    @Mock
    private PersonDTO personMock;

    @BeforeEach
    void setUp() {
        travelMedicalRiskPremiumCalculator =
                new TravelMedicalRiskPremiumCalculator(List.of(
                        elementCalculatorMock1,
                        elementCalculatorMock2
                ));
    }

    @Test
    void shouldReturnCorrectPremium() {
        BigDecimal premiumElement1 = new BigDecimal("4");
        BigDecimal premiumElement2 = new BigDecimal("1.5");
        when(elementCalculatorMock1.calculate(agreementMock, personMock))
                .thenReturn(premiumElement1);
        when(elementCalculatorMock2.calculate(agreementMock, personMock))
                .thenReturn(premiumElement2);
        assertEquals(premiumElement1.multiply(premiumElement2).setScale(2, RoundingMode.HALF_UP),
                travelMedicalRiskPremiumCalculator.calculatePremium(agreementMock, personMock));
    }

    @Test
    void shouldReturnRiskIc() {
        assertEquals("TRAVEL_MEDICAL", travelMedicalRiskPremiumCalculator.getRiskIc());
    }



}