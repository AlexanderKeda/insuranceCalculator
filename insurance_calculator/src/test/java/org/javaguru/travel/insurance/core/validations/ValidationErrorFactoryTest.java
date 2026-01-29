package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.util.ErrorCodeUtil;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidationErrorFactoryTest {

    @Mock private ErrorCodeUtil errorCodeUtilMock;
    @InjectMocks private ValidationErrorFactory validationErrorFactory;

    @Test
    void shouldReturnValidationErrorWithCorrectDescription() {
        when(errorCodeUtilMock.getErrorDescription("ERROR_CODE_0")).thenReturn("Description");
        assertEquals(new ValidationErrorDTO("ERROR_CODE_0", "Description"),
                validationErrorFactory.buildError("ERROR_CODE_0"));
    }

    @Test
    void shouldReturnValidationErrorWithDescriptionUsingPlaceholders() {
        Placeholder placeholder = new Placeholder("name", "value");
        when(errorCodeUtilMock.getErrorDescription("ERROR_CODE_0", List.of(placeholder)))
                .thenReturn("Description with placeholder");
        assertEquals(new ValidationErrorDTO("ERROR_CODE_0", "Description with placeholder"),
                validationErrorFactory.buildError("ERROR_CODE_0", List.of(placeholder)));
    }

}