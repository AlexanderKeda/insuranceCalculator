package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.repositories.CountryDefaultDayRateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryDefaultDayRateElementTest {

    @Mock
    private CountryDefaultDayRateRepository countryDefaultDayRateRepositoryMock;

    @InjectMocks
    private CountryDefaultDayRateElement countryDefaultDayRateElement;

    @Mock
    private AgreementDTO agreementMock;

    @Mock
    private PersonDTO personMock;

    @Test
    void shouldReturnCorrectCountryDefaultDayRate() {
        BigDecimal expectedDayRate = new BigDecimal("1.1");
        when(countryDefaultDayRateRepositoryMock.findByCountryIc(agreementMock.country()))
                .thenReturn(Optional.of(new org.javaguru.travel.insurance.core.domain.CountryDefaultDayRate(1L, "", expectedDayRate)));
        assertEquals(expectedDayRate, countryDefaultDayRateElement.calculate(agreementMock, personMock));
    }

    @Test
    void shouldThrowExceptionWhenDayRateNotFoundInDB() {
        when(countryDefaultDayRateRepositoryMock.findByCountryIc(agreementMock.country()))
                .thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> countryDefaultDayRateElement.calculate(agreementMock, personMock));
    }

}