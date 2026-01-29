package org.javaguru.travel.insurance.core.api.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonDTOTest {

    @Test
    void shouldReturnNewPersonDTO() {
        var person = getCorrectPersonDTO();
        var risk = new RiskDTO("ic", BigDecimal.ZERO);
        var personWithRisks = person.withRisks(List.of(risk));
        assertNotSame(person, personWithRisks);
    }

    @Test
    void shouldReturnWithTheSameFirstName() {
        var person = getCorrectPersonDTO();
        var risk = new RiskDTO("ic", BigDecimal.ZERO);
        var personWithRisks = person.withRisks(List.of(risk));
        assertEquals(person.personFirstName(),
                personWithRisks.personFirstName());
    }

    @Test
    void shouldReturnWithTheSameLastName() {
        var person = getCorrectPersonDTO();
        var risk = new RiskDTO("ic", BigDecimal.ZERO);
        var personWithRisks = person.withRisks(List.of(risk));
        assertEquals(person.personLastName(),
                personWithRisks.personLastName());
    }

    @Test
    void shouldReturnWithTheSameBirthDate() {
        var person = getCorrectPersonDTO();
        var risk = new RiskDTO("ic", BigDecimal.ZERO);
        var personWithRisks = person.withRisks(List.of(risk));
        assertEquals(person.personBirthDate(),
                personWithRisks.personBirthDate());
    }

    @Test
    void shouldReturnWithTheSameLimitLevel() {
        var person = getCorrectPersonDTO();
        var risk = new RiskDTO("ic", BigDecimal.ZERO);
        var personWithRisks = person.withRisks(List.of(risk));
        assertEquals(person.medicalRiskLimitLevel(),
                personWithRisks.medicalRiskLimitLevel());
    }

    @Test
    void shouldReturnWithNewRisks() {
        var person = getCorrectPersonDTO();
        var risk = new RiskDTO("ic", BigDecimal.ZERO);
        var risks = List.of(risk);
        var personWithRisks = person.withRisks(risks);
        assertEquals(risks,
                personWithRisks.risks());
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