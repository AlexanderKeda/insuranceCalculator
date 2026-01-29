package org.javaguru.travel.insurance.core.repositories;

import org.javaguru.travel.insurance.core.domain.CountryDefaultDayRate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@DataJpaTest
class CountryDefaultDayRateRepositoryTest {

    @Autowired private CountryDefaultDayRateRepository countryDefaultDayRateRepository;

    @ParameterizedTest
    @CsvSource({
            "LATVIA, 1.00",
            "SPAIN, 2.50",
            "JAPAN, 3.50"
    })
    void shouldFindRiskTypeValue(String countryIc, String dayRate) {
        Optional<CountryDefaultDayRate> valueOpt = countryDefaultDayRateRepository
                .findByCountryIc(countryIc);
        assertTrue(valueOpt.isPresent());
        assertEquals(countryIc, valueOpt.get().getCountryIc());
        assertEquals(new BigDecimal(dayRate), valueOpt.get().getDefaultDayRate());
    }

    @Test
    void shouldReturnEmptyWhenIcIsFake() {
        Optional<CountryDefaultDayRate> valueOpt = countryDefaultDayRateRepository
                .findByCountryIc("FAKE_COUNTRY");
        assertTrue(valueOpt.isEmpty());
    }

}