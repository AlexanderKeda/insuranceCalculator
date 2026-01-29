package org.javaguru.travel.insurance.core.validations.person;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonBirthDateInPastValidationTest {

    @Mock
    private ValidationErrorFactory errorFactoryMock;

    @InjectMocks
    private PersonBirthDateInPastValidation personBirthDateInPastValidation;

    @Mock
    private PersonDTO personMock;

    @Mock
    private AgreementDTO agreementMock;

    @Test
    void shouldNotReturnErrorWhenBirthDateIsValid() {
        when(personMock.personBirthDate()).thenReturn(LocalDate.now().minusYears(20));
        var errorOpt = personBirthDateInPastValidation
                .validate(agreementMock, personMock);
        assertTrue(errorOpt.isEmpty());
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldReturnErrorWhenBirthDateIsInTheFuture() {
        when(personMock.personBirthDate()).thenReturn(LocalDate.now().plusYears(1));
        when(errorFactoryMock.buildError("ERROR_CODE_13"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_13", "Description"));
        var errorOpt = personBirthDateInPastValidation
                .validate(agreementMock, personMock);
        assertTrue(errorOpt.isPresent());
        assertEquals("ERROR_CODE_13", errorOpt.get().errorCode());
        assertEquals("Description", errorOpt.get().description());
    }

    @Test
    void shouldNotThrowExceptionWhenDateToIsNull() {
        when(personMock.personBirthDate()).thenReturn(null);
        assertDoesNotThrow(() -> personBirthDateInPastValidation.validate(agreementMock, personMock));
    }

}