package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelAgreementValidatorImpTest {

    @Mock
    private TravelAgreementFieldsValidator agreementFieldsValidator;
    @Mock
    private TravelPersonFieldsValidator personFieldsValidator;

    @InjectMocks
    private TravelAgreementValidatorImp agreementValidator;

    @Mock
    private AgreementDTO agreementMock;

    @Test
    void shouldNotReturnErrors() {
        when(agreementFieldsValidator.validate(agreementMock))
                .thenReturn(List.of());
        when(personFieldsValidator.validate(agreementMock))
                .thenReturn(List.of());

        var errors = agreementValidator.validate(agreementMock);
        assertTrue(errors.isEmpty());
    }

    @Test
    void shouldReturnExpectedErrorCount() {
        when(agreementFieldsValidator.validate(agreementMock))
                .thenReturn(List.of(new ValidationErrorDTO("", ""), new ValidationErrorDTO("", "")));
        when(personFieldsValidator.validate(agreementMock))
                .thenReturn(List.of(new ValidationErrorDTO("", "")));

        var errors = agreementValidator.validate(agreementMock);
        assertEquals(3, errors.size());
    }

}