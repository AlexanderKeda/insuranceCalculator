package org.javaguru.travel.insurance.core.validations.agreement.uuid;

import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmptyUuidValidationTest {

    @Mock
    private ValidationErrorFactory errorFactoryMock;

    @InjectMocks
    private EmptyUuidValidation emptyUuidValidation;

    @Test
    void shouldSucceedWhenUuidIsNotEmpty() {
        String uuid = "uuid";
        var errorOpt = emptyUuidValidation.validate(uuid);
        assertTrue(errorOpt.isEmpty());
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldReturnErrorWhenUuidIsEmpty() {
        String uuid = "";
        when(errorFactoryMock.buildError("ERROR_CODE_19"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_19", "description"));
        var errorOpt = emptyUuidValidation.validate(uuid);
        assertTrue(errorOpt.isPresent());
        assertEquals(new ValidationErrorDTO("ERROR_CODE_19", "description"),
                errorOpt.get());
    }

    @Test
    void shouldReturnErrorWhenUuidIsNull() {
        when(errorFactoryMock.buildError("ERROR_CODE_19"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_19", "description"));
        var errorOpt = emptyUuidValidation.validate(null);
        assertTrue(errorOpt.isPresent());
        assertEquals(new ValidationErrorDTO("ERROR_CODE_19", "description"),
                errorOpt.get());
    }

}