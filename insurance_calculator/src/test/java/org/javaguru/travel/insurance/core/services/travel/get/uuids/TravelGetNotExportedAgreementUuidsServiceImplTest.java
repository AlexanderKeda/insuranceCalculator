package org.javaguru.travel.insurance.core.services.travel.get.uuids;

import org.javaguru.travel.insurance.core.api.command.TravelGetNotExportedAgreementUuidsCoreCommand;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelGetNotExportedAgreementUuidsServiceImplTest {

    @Mock
    private AgreementEntityRepository agreementEntityRepositoryMock;

    @InjectMocks
    private TravelGetNotExportedAgreementUuidsServiceImpl travelGetAgreementUuidsService;

    @Test
    void shouldReturnUuidsList() {
        when(agreementEntityRepositoryMock.getNotExportedAgreementUuids())
                .thenReturn(List.of("uuid1", "uuid2", "uuid3"));
        var command = new TravelGetNotExportedAgreementUuidsCoreCommand();
        var result = travelGetAgreementUuidsService.getNotExportedAgreementUuids(command);
        assertFalse(result.agreementUuids().isEmpty());
        assertEquals(3, result.agreementUuids().size());
    }
}