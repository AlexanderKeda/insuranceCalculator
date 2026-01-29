package org.javaguru.travel.insurance.core.repositories.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AgreementEntityRepositoryTest {

    @Autowired
    private AgreementEntityRepository agreementEntityRepository;

    @Test
    void shouldReturnTrueWhenAgreementExist() {
        assertTrue(agreementEntityRepository.existsByUuid("96e46569-5729-40d1-a707-3628b1ca532b"));
    }

    @Test
    void shouldReturnFalseWhenAgreementDoesNotExist() {
        assertFalse(agreementEntityRepository.existsByUuid("fake_uuid"));
    }

    @Test
    void shouldReturnAgreementWhenAgreementExist() {
        var agreementOpt = agreementEntityRepository.findByUuid("96e46569-5729-40d1-a707-3628b1ca532b");
        assertTrue(agreementOpt.isPresent());
    }

    @Test
    void shouldReturnEmptyOptWhenAgreementDoesNotExist() {
        var agreementOpt = agreementEntityRepository.findByUuid("fake_uuid");
        assertFalse(agreementOpt.isPresent());
    }

    @Test
    void shouldReturnUuidsList() {
        var uuidsList = agreementEntityRepository.findAllUuids();
        assertFalse(uuidsList.isEmpty());
        assertEquals(1, uuidsList.size());
        assertEquals("96e46569-5729-40d1-a707-3628b1ca532b", uuidsList.getFirst());
    }

}