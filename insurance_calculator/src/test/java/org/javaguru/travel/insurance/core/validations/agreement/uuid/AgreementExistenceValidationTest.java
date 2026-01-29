package org.javaguru.travel.insurance.core.validations.agreement.uuid;

import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementEntityRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgreementExistenceValidationTest {

    @Mock
    private ValidationErrorFactory errorFactoryMock;

    @Mock
    private AgreementEntityRepository agreementEntityRepositoryMock;

    @InjectMocks
    private AgreementExistenceValidation agreementExistenceValidation;

    @Test
    void shouldNotReturnErrorWhenUuidExist() {
        String uuid = "uuid";
        when(agreementEntityRepositoryMock.existsByUuid(uuid))
                .thenReturn(true);
        var errorOpt = agreementExistenceValidation.validate(uuid);
        assertEquals(Optional.empty(), errorOpt);
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldNotReturnErrorWhenUuidIsEmpty() {
        String uuid = "";
        var errorOpt = agreementExistenceValidation.validate(uuid);
        assertEquals(Optional.empty(), errorOpt);
        Mockito.verifyNoInteractions(agreementEntityRepositoryMock);
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldNotReturnErrorWhenUuidIsNull() {
        var errorOpt = agreementExistenceValidation.validate(null);
        assertEquals(Optional.empty(), errorOpt);
        Mockito.verifyNoInteractions(agreementEntityRepositoryMock);
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldReturnErrorWhenUuidDoesNotExist() {
        String uuid = "fake_uuid";
        when(agreementEntityRepositoryMock.existsByUuid(uuid))
                .thenReturn(false);
        var placeholders = List.of(new Placeholder("NOT_EXISTING_UUID", uuid));
        when(errorFactoryMock.buildError("ERROR_CODE_20", placeholders))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_20", "description"));
        var errorOpt = agreementExistenceValidation.validate(uuid);
        assertTrue(errorOpt.isPresent());
        assertEquals(new ValidationErrorDTO("ERROR_CODE_20", "description"),
                errorOpt.get());
    }
}