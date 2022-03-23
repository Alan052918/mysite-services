package com.example.user_account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/users")
@Slf4j
public class UserAccountController {

    private final UserAccountService userAccountService;

    @Autowired
    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @GetMapping
    public List<UserAccount> getAllUserAccounts() {
        log.info("Request to get all user accounts");
        return userAccountService.getAllUserAccounts();
    }

    @GetMapping(path = "{userAccountId}")
    public UserAccount getUserAccountById(@PathVariable(name = "userAccountId") Long userAccountId) {
        log.info("Request to get user account by id: {}", userAccountId);
        return userAccountService.getUserAccountById(userAccountId);
    }

    @PostMapping(path = "registration")
    public void registerUserAccount(@RequestBody UserAccountRegistrationRequest userAccountRegistrationRequest) {
        log.info("Request to register new user account: {}", userAccountRegistrationRequest);
        userAccountService.registerUserAccount(userAccountRegistrationRequest);
    }

    @PostMapping(path = "{userAccountId}")
    public UserAccount updateUserAccountById(@PathVariable(name = "userAccountId") Long userAccountId,
                                             @RequestParam(name = "name", required = false) String newName,
                                             @RequestParam(name = "email", required = false) String newEmail) {
        log.info("Request to update user account by id: {}, new name: {}, new email: {}", userAccountId, newName, newEmail);
        return userAccountService.updateUserAccountById(userAccountId, newName, newEmail);
    }

    @DeleteMapping(path = "{userAccountId}")
    public void deleteUserAccountById(@PathVariable(name = "userAccountId") Long userAccountId) {
        log.info("Request to delete user account by id: {}", userAccountId);
        userAccountService.deleteUserAccountById(userAccountId);
    }

}
