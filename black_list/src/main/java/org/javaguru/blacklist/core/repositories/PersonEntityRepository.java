package org.javaguru.blacklist.core.repositories;

import org.javaguru.blacklist.core.domain.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface PersonEntityRepository
        extends JpaRepository<PersonEntity, Long> {

    boolean existsByFirstNameAndLastNameAndPersonCodeAndBirthDate(
            String firstName,
            String lastName,
            String personCode,
            LocalDate birthDate
    );

}
