package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.agreement.TravelAgreementFieldsValidation;
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
class TravelAgreementFieldsValidatorTest {

    @Mock
    private TravelAgreementFieldsValidation validation1Mock;
    @Mock
    private TravelAgreementFieldsValidation validation2Mock;

    private TravelAgreementFieldsValidator agreementFieldsValidator;

    @Mock
    private AgreementDTO agreementMock;

    @BeforeEach
    void setUp() {
        var validations = List.of(validation1Mock, validation2Mock);
        agreementFieldsValidator = new TravelAgreementFieldsValidator(validations);
    }

    @Test
    void shouldNotReturnErrors() {
        when(validation1Mock.validate(agreementMock))
                .thenReturn(Optional.empty());
        when(validation1Mock.validateList(agreementMock))
                .thenReturn(List.of());
        when(validation2Mock.validate(agreementMock))
                .thenReturn(Optional.empty());
        when(validation2Mock.validateList(agreementMock))
                .thenReturn(List.of());

        var errors = agreementFieldsValidator.validate(agreementMock);
        assertTrue(errors.isEmpty());
    }

    @Test
    void shouldReturnSingleErrors() {
        when(validation1Mock.validate(agreementMock))
                .thenReturn(Optional.of(new ValidationErrorDTO("", "")));
        when(validation1Mock.validateList(agreementMock))
                .thenReturn(List.of());
        when(validation2Mock.validate(agreementMock))
                .thenReturn(Optional.of(new ValidationErrorDTO("", "")));
        when(validation2Mock.validateList(agreementMock))
                .thenReturn(List.of());

        var errors = agreementFieldsValidator.validate(agreementMock);
        assertEquals(2, errors.size());
    }

    @Test
    void shouldReturnListErrors() {
        when(validation1Mock.validate(agreementMock))
                .thenReturn(Optional.empty());
        when(validation1Mock.validateList(agreementMock))
                .thenReturn(List.of(new ValidationErrorDTO("", ""), new ValidationErrorDTO("", "")));
        when(validation2Mock.validate(agreementMock))
                .thenReturn(Optional.empty());
        when(validation2Mock.validateList(agreementMock))
                .thenReturn(List.of(new ValidationErrorDTO("", "")));

        var errors = agreementFieldsValidator.validate(agreementMock);
        assertEquals(3, errors.size());
    }

    @Test
    void shouldReturnExpectedErrorCount() {
        when(validation1Mock.validate(agreementMock))
                .thenReturn(Optional.of(new ValidationErrorDTO("", "")));
        when(validation1Mock.validateList(agreementMock))
                .thenReturn(List.of(new ValidationErrorDTO("", ""), new ValidationErrorDTO("", "")));
        when(validation2Mock.validate(agreementMock))
                .thenReturn(Optional.of(new ValidationErrorDTO("", "")));
        when(validation2Mock.validateList(agreementMock))
                .thenReturn(List.of(new ValidationErrorDTO("", "")));

        var errors = agreementFieldsValidator.validate(agreementMock);
        assertEquals(5, errors.size());
    }

}