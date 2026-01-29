package org.javaguru.travel.insurance.core.services.travel.calculate;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.entities.PersonEntity;
import org.javaguru.travel.insurance.core.repositories.entities.PersonEntityRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class PersonEntityFactory {

private final PersonEntityRepository personEntityRepository;

    PersonEntity createPersonEntity (PersonDTO personDTO) {
        var personOpt = personEntityRepository.findByFirstNameAndLastNameAndPersonCode(
                personDTO.personFirstName(),
                personDTO.personLastName(),
                personDTO.personCode()
        );
        if (personOpt.isPresent()) {
            return personOpt.get();
        } else {
            PersonEntity person = new PersonEntity(
                    null,
                    personDTO.personFirstName(),
                    personDTO.personLastName(),
                    personDTO.personCode(),
                    personDTO.personBirthDate()
            );
            return personEntityRepository.save(person);
        }
    }

}
