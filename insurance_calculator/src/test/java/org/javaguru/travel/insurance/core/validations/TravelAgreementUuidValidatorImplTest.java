package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.agreement.uuid.TravelAgreementUuidValidation;
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
class TravelAgreementUuidValidatorImplTest {

    @Mock
    private TravelAgreementUuidValidation agreementUuidValidationMock1;

    @Mock
    private TravelAgreementUuidValidation agreementUuidValidationMock2;

    private TravelAgreementUuidValidatorImpl travelAgreementUuidValidator;

    @BeforeEach
    void setUp() {
        travelAgreementUuidValidator = new TravelAgreementUuidValidatorImpl(List.of(
                agreementUuidValidationMock1,
                agreementUuidValidationMock2
        ));
    }

    @Test
    void shouldNotReturnErrors() {
        String uuid = "uuid";
        when(agreementUuidValidationMock1.validate(uuid))
                .thenReturn(Optional.empty());
        when(agreementUuidValidationMock2.validate(uuid))
                .thenReturn(Optional.empty());

        var errors = travelAgreementUuidValidator.validate(uuid);
        assertTrue(errors.isEmpty());
    }

    @Test
    void shouldReturnSingleErrors() {
        String uuid = "uuid";
        when(agreementUuidValidationMock1.validate(uuid))
                .thenReturn(Optional.of(new ValidationErrorDTO("", "")));
        when(agreementUuidValidationMock2.validate(uuid))
                .thenReturn(Optional.empty());

        var errors = travelAgreementUuidValidator.validate(uuid);
        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
    }

    @Test
    void shouldReturnExpectedErrorCount() {
        String uuid = "uuid";
        when(agreementUuidValidationMock1.validate(uuid))
                .thenReturn(Optional.of(new ValidationErrorDTO("", "")));
        when(agreementUuidValidationMock2.validate(uuid))
                .thenReturn(Optional.of(new ValidationErrorDTO("", "")));

        var errors = travelAgreementUuidValidator.validate(uuid);
        assertFalse(errors.isEmpty());
        assertEquals(2, errors.size());
    }

}