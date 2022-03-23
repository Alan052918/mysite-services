package com.example.user_account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserAccountController {

    private final UserAccountService userAccountService;

    @Autowired
    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @GetMapping
    public List<UserAccount> getAllUserAccounts() {
        return userAccountService.getAllUserAccounts();
    }

    @GetMapping(path = "{userAccountId}")
    public UserAccount getUserAccountById(@PathVariable(name = "userAccountId") Long userAccountId) {
        return userAccountService.getUserAccountById(userAccountId);
    }

    @PostMapping(path = "registration")
    public void registerUserAccount(@RequestBody UserAccountRegistrationRequest userAccountRegistrationRequest) {
        userAccountService.registerUserAccount(userAccountRegistrationRequest);
    }

    @PostMapping(path = "{userAccountId}")
    public UserAccount updateUserAccountById(@PathVariable(name = "userAccountId") Long userAccountId,
                                             @RequestParam(name = "name", required = false) String newName,
                                             @RequestParam(name = "email", required = false) String newEmail) {
        return userAccountService.updateUserAccountById(userAccountId, newName, newEmail);
    }

    @DeleteMapping(path = "{userAccountId}")
    public void deleteUserAccountById(@PathVariable(name = "userAccountId") Long userAccountId) {
        userAccountService.deleteUserAccountById(userAccountId);
    }

}
