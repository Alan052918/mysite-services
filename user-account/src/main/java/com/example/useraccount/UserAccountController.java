package com.example.useraccount;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        List<UserAccount> allUserAccounts = userAccountService.getAllUserAccounts();
        return userAccountModelAssembler.toCollectionModel(allUserAccounts);
    }

    @GetMapping(path = "{userAccountId}")
    public EntityModel<UserAccount> getUserAccountById(@PathVariable(name = "userAccountId") Long userAccountId) {
        log.info("Request to get user account by id: {}", userAccountId);
        UserAccount userAccountById = userAccountService.getUserAccountById(userAccountId);
        return userAccountModelAssembler.toModel(userAccountById);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<UserAccount> createUserAccount(@RequestBody UserAccountCreationRequest userAccountCreationRequest) {
        log.info("Request to create new user account: {}", userAccountCreationRequest);
        UserAccount createdUserAccount = userAccountService.createUserAccount(userAccountCreationRequest);
        return userAccountModelAssembler.toModel(createdUserAccount);
    }

    @PostMapping(path = "{userAccountId}")
    public UserAccount updateUserAccountById(@PathVariable(name = "userAccountId") Long userAccountId,
                                             @RequestParam(name = "name", required = false) String newName,
                                             @RequestParam(name = "email", required = false) String newEmail) {
        log.info("Request to update user account by id: {}, new name: {}, new email: {}", userAccountId, newName, newEmail);
        return userAccountService.updateUserAccountById(userAccountId, newName, newEmail);
    }

    @DeleteMapping(path = "{userAccountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteUserAccountById(@PathVariable(name = "userAccountId") Long userAccountId) {
        log.info("Request to delete user account by id: {}", userAccountId);
        userAccountService.deleteUserAccountById(userAccountId);
        return ResponseEntity.noContent().build();
    }

}
