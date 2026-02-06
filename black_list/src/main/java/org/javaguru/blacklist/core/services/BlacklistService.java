package org.javaguru.blacklist.core.services;

import org.javaguru.blacklist.core.api.command.PersonAddCommand;
import org.javaguru.blacklist.core.api.command.PersonAddResult;
import org.javaguru.blacklist.core.api.command.PersonCheckCommand;
import org.javaguru.blacklist.core.api.command.PersonCheckResult;

public interface BlacklistService {

    PersonCheckResult check(PersonCheckCommand command);

    PersonAddResult add(PersonAddCommand command);

}
