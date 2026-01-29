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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmptyPersonBirthDateValidationTest {

    @Mock
    private ValidationErrorFactory errorFactoryMock;

    @InjectMocks
    private EmptyPersonBirthDateValidation emptyPersonBirthDateValidation;

    @Mock
    private PersonDTO personMock;

    @Mock
    private AgreementDTO agreementMock;

    @Test
    void shouldNotReturnErrorWhenDateFromIsValid() {
        when(personMock.personBirthDate())
                .thenReturn(LocalDate.now());
        var errorOpt = emptyPersonBirthDateValidation.validate(agreementMock, personMock);
        assertTrue(errorOpt.isEmpty());
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldReturnErrorWhenDateFromIsNull() {
        when(personMock.personBirthDate())
                .thenReturn(null);
        when(errorFactoryMock.buildError("ERROR_CODE_12"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_12", "description"));
        var errorOpt = emptyPersonBirthDateValidation.validate(agreementMock, personMock);
        assertTrue(errorOpt.isPresent());
        assertEquals("ERROR_CODE_12", errorOpt.get().errorCode());
        assertEquals("description", errorOpt.get().description());
    }

}