package com.example.useraccount;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceTest {

    private UserAccountService testService;
    private AutoCloseable autoCloseable;

    @Mock
    private UserAccountRepository mockUserAccountRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        testService = new UserAccountService(mockUserAccountRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void GetAllUserAccounts_Success() {
        // when
        testService.getAllUserAccounts();

        // then
        verify(mockUserAccountRepository).findAll();
    }

    @Test
    @Disabled
    void GetUserAccountById_ExistingId_True() {
        // TODO: implement this
    }

    @Test
    @Disabled
    void GetUserAccountById_NotExistingId_ExceptionThrown() {
        // TODO: implement this
    }

    @Test
    void CreateUserAccount_NewEmail_Success() {
        // given
        UserAccountCreationRequest testRequest = UserAccountCreationRequest.builder()
                .name("Alex")
                .email("alex@gmail.com")
                .build();

        // when
        testService.createUserAccount(testRequest);

        // then
        ArgumentCaptor<UserAccount> userAccountArgumentCaptor = ArgumentCaptor.forClass(UserAccount.class);
        verify(mockUserAccountRepository).save(userAccountArgumentCaptor.capture());
        UserAccount userAccountArgumentCaptorValue = userAccountArgumentCaptor.getValue();
        assertThat(userAccountArgumentCaptorValue.getName()).isEqualTo(testRequest.getName());
        assertThat(userAccountArgumentCaptorValue.getEmail()).isEqualTo(testRequest.getEmail());
    }

    @Test
    void CreateUserAccount_ConflictEmail_ExceptionThrown() {
        // given
        LocalDateTime setupTime = LocalDateTime.now();
        UserAccount setupAccount = UserAccount.builder()
                .name("Jamila")
                .email("jamila@gmail.com")
                .dateTimeCreated(setupTime)
                .dateTimeUpdated(setupTime)
                .build();
        mockUserAccountRepository.save(setupAccount);
        UserAccountCreationRequest testRequest = UserAccountCreationRequest.builder()
                .name("James")
                .email("jamila@gmail.com")
                .build();
        given(mockUserAccountRepository.existsByEmail(testRequest.getEmail()))
                .willReturn(true);

        // when
        // then
        assertThatThrownBy(() -> testService.createUserAccount(testRequest))
                .isInstanceOf(UserAccountEmailConflictException.class)
                .hasMessageContaining("Requested email " + testRequest.getEmail() + " conflicts with existing user accounts.");
        verify(mockUserAccountRepository, times(1)).save(any());
    }

    @Test
    @Disabled
    void UpdateUserAccountById_ExistingIdNewEmail_Success() {
        // TODO: implement this
    }

    @Test
    void UpdateUserAccountById_NotExistingId_ExceptionThrown() {
        // given
        Long testId = 1L;

        // when
        // then
        assertThatThrownBy(() -> testService.updateUserAccountById(testId, null, null))
                .isInstanceOf(UserAccountNotFoundException.class)
                .hasMessageContaining("User account by id " + testId + " was not found.");
        verify(mockUserAccountRepository, never()).save(any());
    }

    @Test
    @Disabled
    void UpdateUserAccountById_ConflictEmail_ExceptionThrown() {
        // TODO: implement this
    }

    @Test
    @Disabled
    void deleteUserAccountById() {
        // TODO: implement this
    }

}
