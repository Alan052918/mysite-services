package com.example.user_account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/users")
@Slf4j
public class UserAccountController {

    private final UserAccountService userAccountService;
    private final UserAccountModelAssembler userAccountModelAssembler;

    @Autowired
    public UserAccountController(UserAccountService userAccountService, UserAccountModelAssembler userAccountModelAssembler) {
        this.userAccountService = userAccountService;
        this.userAccountModelAssembler = userAccountModelAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<UserAccount>> getAllUserAccounts() {
        log.info("Request to get all user accounts");
        List<UserAccount> userAccounts = userAccountService.getAllUserAccounts();
        return userAccountModelAssembler.toCollectionModel(userAccounts);
    }

    @GetMapping(path = "{userAccountId}")
    public EntityModel<UserAccount> getUserAccountById(@PathVariable(name = "userAccountId") Long userAccountId) {
        log.info("Request to get user account by id: {}", userAccountId);
        UserAccount userAccount = userAccountService.getUserAccountById(userAccountId);
        return userAccountModelAssembler.toModel(userAccount);
    }

    @PostMapping(path = "registration")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<UserAccount> registerUserAccount(@RequestBody UserAccountRegistrationRequest userAccountRegistrationRequest) {
        log.info("Request to register new user account: {}", userAccountRegistrationRequest);
        UserAccount userAccount = userAccountService.registerUserAccount(userAccountRegistrationRequest);
        return userAccountModelAssembler.toModel(userAccount);
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
