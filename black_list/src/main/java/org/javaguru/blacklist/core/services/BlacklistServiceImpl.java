package org.javaguru.blacklist.core.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.blacklist.core.api.command.PersonAddCommand;
import org.javaguru.blacklist.core.api.command.PersonAddResult;
import org.javaguru.blacklist.core.api.command.PersonCheckCommand;
import org.javaguru.blacklist.core.api.command.PersonCheckResult;
import org.javaguru.blacklist.core.domain.PersonEntity;
import org.javaguru.blacklist.core.repositories.PersonEntityRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
class BlacklistServiceImpl implements BlacklistService {

    private final PersonEntityRepository personEntityRepository;

    @Override
    public PersonCheckResult check(PersonCheckCommand command) {
        boolean blackListed = personEntityRepository
                .existsByFirstNameAndLastNameAndPersonCodeAndBirthDate(
                        command.person().personFirstName(),
                        command.person().personLastName(),
                        command.person().personCode(),
                        command.person().personBirthDate()
                );
        log.info(
                "Is person in blacklist - {}. {}",
                blackListed,
                command.person()
        );
        return new PersonCheckResult(command.person(), blackListed);
    }

    @Override
    public PersonAddResult add(PersonAddCommand command) {
        PersonEntity personToAdd = new PersonEntity(
                null,
                command.person().personFirstName(),
                command.person().personLastName(),
                command.person().personCode(),
                command.person().personBirthDate()
        );
        PersonEntity savedPerson = personEntityRepository.save(personToAdd);
        log.info(
                "Person saved to blacklist: {}, {}, {}, {}.",
                savedPerson.getFirstName(),
                savedPerson.getLastName(),
                savedPerson.getPersonCode(),
                savedPerson.getBirthDate());
        return new PersonAddResult(command.person(), true);
    }
}
