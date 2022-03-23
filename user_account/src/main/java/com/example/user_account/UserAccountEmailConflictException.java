package com.example.user_account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAccountEmailConflictException extends RuntimeException {

    public UserAccountEmailConflictException(String requestedEmail) {
        super("Requested email " + requestedEmail + " conflicts with existing user accounts.");
    }

}
