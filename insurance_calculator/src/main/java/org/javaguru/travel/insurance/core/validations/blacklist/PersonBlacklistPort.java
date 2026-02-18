package org.javaguru.travel.insurance.core.validations.blacklist;

import org.javaguru.travel.insurance.core.api.dto.PersonBlacklistState;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;

public interface PersonBlacklistPort {

    PersonBlacklistState personCheck(PersonDTO person);

}
