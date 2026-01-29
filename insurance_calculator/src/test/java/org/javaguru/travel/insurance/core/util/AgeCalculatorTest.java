package org.javaguru.travel.insurance.core.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AgeCalculatorTest {

    private final AgeCalculator ageCalculator = new AgeCalculator();

    @ParameterizedTest
    @CsvSource({
            "10",
            "30",
            "90",
            "120",
            "150",
            "200"
    })
    void shouldDaysBetweenBePositive(long age) {
        LocalDate birthDate = LocalDate.now().minusYears(age);
        assertEquals(
                age,
                ageCalculator.calculate(birthDate)
        );
    }

    @ParameterizedTest
    @CsvSource({
                    "1",
                    "3",
                    "5",
                    "7",
                    "10"
    })
    void shouldReturnNegativeOneWhenAgeIsNegative(long yearsInFuture) {
        assertEquals(
                -1L,
                ageCalculator.calculate(LocalDate.now().plusYears(yearsInFuture))
        );
    }

}