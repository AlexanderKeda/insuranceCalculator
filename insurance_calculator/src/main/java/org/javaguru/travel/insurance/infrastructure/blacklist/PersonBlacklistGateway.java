package org.javaguru.travel.insurance.infrastructure.blacklist;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.PersonBlacklistState;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.validations.blacklist.PersonBlacklistPort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class PersonBlacklistGateway implements PersonBlacklistPort {

    private final PersonBlacklistClient personBlacklistClient;

    @Override
    public PersonBlacklistState personCheck(PersonDTO person) {
        try {
            PersonCheckResultDTO resultDTO = personBlacklistClient.checkPerson(person);
            return resultDTO.blackListed()
                    ? PersonBlacklistState.BLACKLISTED
                    : PersonBlacklistState.NOT_BLACKLISTED;
        } catch (Exception e) {
            return PersonBlacklistState.UNKNOWN;
        }
    }
}
