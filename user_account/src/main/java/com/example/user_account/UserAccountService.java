package com.example.user_account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public List<UserAccount> getAllUserAccounts() {
        log.info("Get all user accounts");
        return userAccountRepository.findAll();
    }

    public UserAccount getUserAccountById(Long userAccountId) {
        log.info("Get user account by id: {}", userAccountId);
        return userAccountRepository.findById(userAccountId)
                .orElseThrow(() -> new UserAccountNotFoundException(userAccountId));
    }

    public UserAccount createUserAccount(UserAccountCreationRequest userAccountCreationRequest) {
        log.info("Create new user account: {}", userAccountCreationRequest);
        String requestedEmail = userAccountCreationRequest.getEmail();
        boolean requestedConflictEmail = userAccountRepository.existsByEmail(requestedEmail);
        if (requestedConflictEmail) {
            throw new UserAccountEmailConflictException(requestedEmail);
        }
        LocalDateTime requestDateTime = LocalDateTime.now();
        UserAccount userAccount = UserAccount.builder()
                .name(userAccountCreationRequest.getName())
                .email(userAccountCreationRequest.getEmail())
                .dateTimeCreated(requestDateTime)
                .dateTimeUpdated(requestDateTime)
                .build();
        return userAccountRepository.saveAndFlush(userAccount);
    }

    public UserAccount updateUserAccountById(Long userAccountId, String newName, String newEmail) {
        log.info("Update user account by id: {}, new name: {}, new email: {}", userAccountId, newName, newEmail);
        UserAccount userAccount = userAccountRepository.findById(userAccountId)
                .orElseThrow(() -> new UserAccountNotFoundException(userAccountId));
        if (newName != null && newName.length() > 0 && !Objects.equals(newName, userAccount.getName())) {
            userAccount.setName(newName);
        }
        if (newEmail != null && newEmail.length() > 0 && !Objects.equals(newEmail, userAccount.getEmail())) {
            boolean requestedConflictEmail = userAccountRepository.existsByEmail(newEmail);
            if (requestedConflictEmail) {
                throw new UserAccountEmailConflictException(newEmail);
            }
            userAccount.setEmail(newEmail);
        }
        return userAccountRepository.saveAndFlush(userAccount);
    }

    public void deleteUserAccountById(Long userAccountId) {
        log.info("Delete user account by id: {}", userAccountId);
        if (!userAccountRepository.existsById(userAccountId)) {
            throw new UserAccountNotFoundException(userAccountId);
        }
        userAccountRepository.deleteById(userAccountId);
    }

}
