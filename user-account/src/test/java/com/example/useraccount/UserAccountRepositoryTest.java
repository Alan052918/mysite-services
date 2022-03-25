package com.example.useraccount;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserAccountRepositoryTest {

    @Autowired
    private UserAccountRepository testRepository;

    @AfterEach
    void tearDown() {
        testRepository.deleteAll();
    }

    @Test
    void ExistsByEmail_ExistingEmail_True() {
        // given
        String testName = "Jamila";
        String testEmail = "jamila@gmail.com";
        LocalDateTime testDateTime = LocalDateTime.now();
        UserAccount testAccount = UserAccount.builder()
                .name(testName)
                .email(testEmail)
                .dateTimeCreated(testDateTime)
                .dateTimeUpdated(testDateTime)
                .build();
        testRepository.saveAndFlush(testAccount);

        // when
        boolean existsByEmail = testRepository.existsByEmail(testEmail);

        // then
        assertThat(existsByEmail).isTrue();
    }

    @Test
    void ExistsByEmail_NonexistingEmail_False() {
        // given
        String email = "jamila@gmail.com";

        // when
        boolean existsByEmail = testRepository.existsByEmail(email);

        // then
        assertThat(existsByEmail).isFalse();
    }
}
