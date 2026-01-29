package org.javaguru.travel.insurance.core.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AgeCoefficientRepositoryTest {

    @Autowired
    private AgeCoefficientRepository ageCoefficientRepository;

    @ParameterizedTest
    @CsvSource({
            "4, 1.10",
            "7, 0.70",
            "11, 1.00",
            "40, 1.10",
            "60, 1.20",
            "100, 1.50",
    })
    void shouldFindCoefficientValue(int age, BigDecimal expectedCoefficient) {
        var ageCoefficientOpt = ageCoefficientRepository
                .findByAge(age);
        assertTrue(ageCoefficientOpt.isPresent());
        assertEquals(expectedCoefficient, ageCoefficientOpt.get().getCoefficient());
    }

    @Test
    void shouldReturnEmptyWhenAgeIsInvalid() {
        var ageCoefficientOpt = ageCoefficientRepository
                .findByAge(151);
        assertTrue(ageCoefficientOpt.isEmpty());
    }

}