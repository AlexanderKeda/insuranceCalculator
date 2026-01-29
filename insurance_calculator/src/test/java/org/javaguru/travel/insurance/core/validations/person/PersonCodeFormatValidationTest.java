package org.javaguru.travel.insurance.core.validations.person;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonCodeFormatValidationTest {

    @Mock
    private ValidationErrorFactory validationErrorFactoryMock;

    @InjectMocks
    private PersonCodeFormatValidation personCodeFormatValidation;

    @Mock
    private AgreementDTO agreementMock;

    @Mock
    private PersonDTO personMock;

    @Test
    void shouldSucceedWhenCodeIsValid() {
        var personCode = "111111-11111";
        when(personMock.personCode()).thenReturn(personCode);
        var result = personCodeFormatValidation.validate(agreementMock, personMock);
        assertTrue(result.isEmpty());
        Mockito.verifyNoInteractions(validationErrorFactoryMock);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldSucceedWhenCodeIsNullOrEmpty(String personCode) {
        when(personMock.personCode()).thenReturn(personCode);
        var result = personCodeFormatValidation.validate(agreementMock, personMock);
        assertTrue(result.isEmpty());
        Mockito.verifyNoInteractions(validationErrorFactoryMock);
    }

    @Test
    void shouldReturnErrorWhenCodeIsInvalid() {
        String personCode = "invalid_code";
        var error = new ValidationErrorDTO("code", "description");
        when(personMock.personCode()).thenReturn(personCode);
        when(validationErrorFactoryMock.buildError("ERROR_CODE_22"))
                .thenReturn(error);
        var result = personCodeFormatValidation.validate(agreementMock, personMock);
        assertTrue(result.isPresent());
        assertEquals(error, result.get());
    }

}