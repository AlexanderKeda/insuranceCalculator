package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.person.TravelPersonFieldsValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelPersonFieldsValidatorTest {

    @Mock
    private TravelPersonFieldsValidation validation1Mock;
    @Mock
    private TravelPersonFieldsValidation validation2Mock;

    private TravelPersonFieldsValidator personFieldsValidator;

    @Mock
    private PersonDTO person1Mock;

    @Mock
    private PersonDTO person2Mock;

    @Mock
    private AgreementDTO agreement;

    private List<PersonDTO> persons;

    @BeforeEach
    void setUp() {
        var validations = List.of(validation1Mock, validation2Mock);
        personFieldsValidator = new TravelPersonFieldsValidator(validations);
        persons = List.of(person1Mock, person2Mock);
    }

    @Test
    void shouldNotReturnErrors() {
        when(validation1Mock.validate(agreement, person1Mock))
                .thenReturn(Optional.empty());
        when(validation1Mock.validateList(agreement, person1Mock))
                .thenReturn(List.of());
        when(validation1Mock.validate(agreement, person2Mock))
                .thenReturn(Optional.empty());
        when(validation1Mock.validateList(agreement, person2Mock))
                .thenReturn(List.of());

        when(validation2Mock.validate(agreement, person1Mock))
                .thenReturn(Optional.empty());
        when(validation2Mock.validateList(agreement, person1Mock))
                .thenReturn(List.of());
        when(validation2Mock.validate(agreement, person2Mock))
                .thenReturn(Optional.empty());
        when(validation2Mock.validateList(agreement, person2Mock))
                .thenReturn(List.of());

        when(agreement.persons()).thenReturn(persons);

        var errors = personFieldsValidator.validate(agreement);
        assertTrue(errors.isEmpty());
    }

    @Test
    void shouldNotReturnErrorsWhenPersonsIsNull() {
        when(agreement.persons()).thenReturn(null);
        var errors = personFieldsValidator.validate(agreement);
        assertTrue(errors.isEmpty());
    }

    @Test
    void shouldReturnSingleErrors() {
        when(validation1Mock.validate(agreement, person1Mock))
                .thenReturn(Optional.of(new ValidationErrorDTO("", "")));
        when(validation1Mock.validateList(agreement, person1Mock))
                .thenReturn(List.of());
        when(validation1Mock.validate(agreement, person2Mock))
                .thenReturn(Optional.of(new ValidationErrorDTO("", "")));
        when(validation1Mock.validateList(agreement, person2Mock))
                .thenReturn(List.of());

        when(validation2Mock.validate(agreement, person1Mock))
                .thenReturn(Optional.of(new ValidationErrorDTO("", "")));
        when(validation2Mock.validateList(agreement, person1Mock))
                .thenReturn(List.of());
        when(validation2Mock.validate(agreement, person2Mock))
                .thenReturn(Optional.of(new ValidationErrorDTO("", "")));
        when(validation2Mock.validateList(agreement, person2Mock))
                .thenReturn(List.of());

        when(agreement.persons()).thenReturn(persons);

        var errors = personFieldsValidator.validate(agreement);
        assertEquals(4, errors.size());
    }

    @Test
    void shouldReturnListErrors() {
        when(validation1Mock.validate(agreement, person1Mock))
                .thenReturn(Optional.empty());
        when(validation1Mock.validateList(agreement, person1Mock))
                .thenReturn(List.of(new ValidationErrorDTO("", ""), new ValidationErrorDTO("", "")));
        when(validation1Mock.validate(agreement, person2Mock))
                .thenReturn(Optional.empty());
        when(validation1Mock.validateList(agreement, person2Mock))
                .thenReturn(List.of(new ValidationErrorDTO("", "")));

        when(validation2Mock.validate(agreement, person1Mock))
                .thenReturn(Optional.empty());
        when(validation2Mock.validateList(agreement, person1Mock))
                .thenReturn(List.of(new ValidationErrorDTO("", ""), new ValidationErrorDTO("", "")));
        when(validation2Mock.validate(agreement, person2Mock))
                .thenReturn(Optional.empty());
        when(validation2Mock.validateList(agreement, person2Mock))
                .thenReturn(List.of(new ValidationErrorDTO("", "")));

        when(agreement.persons()).thenReturn(persons);

        var errors = personFieldsValidator.validate(agreement);
        assertEquals(6, errors.size());
    }

    @Test
    void shouldReturnExpectedErrorCount() {
        when(validation1Mock.validate(agreement, person1Mock))
                .thenReturn(Optional.of(new ValidationErrorDTO("", "")));
        when(validation1Mock.validateList(agreement, person1Mock))
                .thenReturn(List.of(new ValidationErrorDTO("", ""), new ValidationErrorDTO("", "")));
        when(validation1Mock.validate(agreement, person2Mock))
                .thenReturn(Optional.of(new ValidationErrorDTO("", "")));
        when(validation1Mock.validateList(agreement, person2Mock))
                .thenReturn(List.of(new ValidationErrorDTO("", "")));

        when(validation2Mock.validate(agreement, person1Mock))
                .thenReturn(Optional.of(new ValidationErrorDTO("", "")));
        when(validation2Mock.validateList(agreement, person1Mock))
                .thenReturn(List.of(new ValidationErrorDTO("", ""), new ValidationErrorDTO("", "")));
        when(validation2Mock.validate(agreement, person2Mock))
                .thenReturn(Optional.of(new ValidationErrorDTO("", "")));
        when(validation2Mock.validateList(agreement, person2Mock))
                .thenReturn(List.of(new ValidationErrorDTO("", "")));

        when(agreement.persons()).thenReturn(persons);

        var errors = personFieldsValidator.validate(agreement);
        assertEquals(10, errors.size());
    }


}