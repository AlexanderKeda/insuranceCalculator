package org.javaguru.blacklist.adaptets.rest;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.blacklist.core.api.command.PersonAddCommand;
import org.javaguru.blacklist.core.api.command.PersonAddResult;
import org.javaguru.blacklist.core.api.command.PersonCheckCommand;
import org.javaguru.blacklist.core.api.command.PersonCheckResult;
import org.javaguru.blacklist.core.api.dto.PersonDTO;
import org.javaguru.blacklist.core.services.BlacklistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/blacklist/person")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class PersonBlacklistController {

    private final BlacklistService blacklistService;

    @PostMapping(
            path = "/add",
            consumes = "application/json",
            produces = "application/json")
    public PersonAddResult addPerson(@RequestBody @Valid PersonDTO person) {
        log.info("Request to add: {}", person);
        return blacklistService.add(new PersonAddCommand(person));
    }

    @PostMapping(
            path = "/check",
            consumes = "application/json",
            produces = "application/json")
    public PersonCheckResult checkPerson(@RequestBody @Valid PersonDTO person) {
        log.info("Request to check: {}", person);
        return blacklistService.check(new PersonCheckCommand(person));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handle(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );
        log.info("Bad request: {}", errors);
        return ResponseEntity.badRequest().body(errors);
    }

}