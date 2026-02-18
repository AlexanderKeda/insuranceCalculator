package org.javaguru.travel.insurance.core.validations.blacklist;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.travel.insurance.core.api.dto.PersonBlacklistState;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
class PersonBlacklistValidatorImpl implements PersonBlacklistValidator {

    private final ValidationErrorFactory validationErrorFactory;
    private final PersonBlacklistPort personBlacklistPort;

    @Override
    public List<ValidationErrorDTO> validate(PersonDTO person) {
        PersonBlacklistState state = personBlacklistPort.personCheck(person);
        switch (state) {
            case BLACKLISTED -> {return List.of(validationErrorFactory.buildError("ERROR_CODE_25"));}
            case NOT_BLACKLISTED -> {return List.of();}
            case UNKNOWN ->{return List.of(validationErrorFactory.buildError("ERROR_CODE_26"));}
            default -> {
                log.error("Unknown person blacklist state: {}", state);
                throw new IllegalStateException("Unknown person blacklist state!");
            }
        }
    }
}
