package org.javaguru.travel.insurance.core.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ErrorCodeUtilTest {

    @Mock
    Properties propMock;

    @InjectMocks
    ErrorCodeUtil errorCodeUtil;

    @Test
    void shouldReturnDescriptionWhenErrorCodeExist() {
        when(propMock.containsKey("ERROR_CODE")).thenReturn(true);
        when(propMock.getProperty("ERROR_CODE")).thenReturn("Description");
        assertEquals("Description",
                errorCodeUtil.getErrorDescription("ERROR_CODE"));
    }

    @Test
    void shouldReturnDefaultMessageForUnknownErrorCode() {
        when(propMock.containsKey("UNKNOWN_ERROR_CODE")).thenReturn(false);
        assertEquals("Unknown error code!",
                errorCodeUtil.getErrorDescription("UNKNOWN_ERROR_CODE"));
    }

    @Test
    void shouldReturnDescriptionWithPlaceholderWhenErrorCodeExist() {
        Placeholder placeholder = new Placeholder("placeholderName", "value");
        when(propMock.containsKey("ERROR_CODE")).thenReturn(true);
        when(propMock.getProperty("ERROR_CODE")).thenReturn("Description with {placeholderName}");
        assertEquals("Description with value",
                errorCodeUtil.getErrorDescription("ERROR_CODE", List.of(placeholder)));
    }

}