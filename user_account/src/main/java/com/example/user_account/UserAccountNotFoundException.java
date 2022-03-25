package com.example.user_account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserAccountNotFoundException extends RuntimeException {

    public UserAccountNotFoundException(Long userAccountId) {
        super("User account by id " + userAccountId + " was not found.");
    }

}
