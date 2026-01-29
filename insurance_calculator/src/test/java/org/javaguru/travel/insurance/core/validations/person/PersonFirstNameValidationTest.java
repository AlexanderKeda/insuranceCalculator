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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PersonFirstNameValidationTest {

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private PersonFirstNameValidation personFirstNameValidation;

    @Mock
    private PersonDTO personMock;

    @Mock
    private AgreementDTO agreementMock;

    @Test
    void shouldNotReturnErrorWhenFirstNameIsValid() {
        when(personMock.personFirstName()).thenReturn("Ivan");
        var errorOptional = personFirstNameValidation.validate(agreementMock, personMock);
        assertTrue(errorOptional.isEmpty());
        Mockito.verifyNoInteractions(validationErrorFactory);
    }

    @Test
    void shouldReturnErrorWhenFirstNameIsNull() {
        when(personMock.personFirstName()).thenReturn(null);
        when(validationErrorFactory.buildError("ERROR_CODE_1"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_1", "Description"));
        var errorOptional = personFirstNameValidation.validate(agreementMock, personMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("ERROR_CODE_1", errorOptional.get().errorCode());
        assertEquals("Description", errorOptional.get().description());
    }

    @Test
    void shouldReturnErrorWhenFirstNameIsEmpty() {
        when(personMock.personFirstName()).thenReturn("");
        when(validationErrorFactory.buildError("ERROR_CODE_1"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_1", "Description"));
        var errorOptional = personFirstNameValidation.validate(agreementMock, personMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("ERROR_CODE_1", errorOptional.get().errorCode());
        assertEquals("Description", errorOptional.get().description());
    }

}