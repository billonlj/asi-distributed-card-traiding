package com.asi.service;


import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import com.asi.dto.CardInstanceDto;
import com.asi.model.User;
import com.asi.repository.UserRepository;
import com.asi.rest.card.CardRestConsumer;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    @Spy
    UserService userService;

    @Mock
    UserRepository userRepositoryMock;

    @Mock
    CardRestConsumer cardRestConsumerMock;
    
    private List<CardInstanceDto>  listWithCards = Arrays.asList(new CardInstanceDto[]{new CardInstanceDto(),new CardInstanceDto(),new CardInstanceDto()});

    private List<CardInstanceDto> listWithoutCards = Arrays.asList(new CardInstanceDto[]{});

    // =========== addUser ===========
    @Test
    void testAddUserWithCards() {
        User user = new User(1, "TestName", "TestSurname", "TestEmail", 100, "pwd");
        when(cardRestConsumerMock.generateCardsForNewUser(user.getIdUser())).thenReturn(ResponseEntity.ok(listWithCards));
        Assertions.assertThat(userService.addUser(user)).isTrue();
    }

    @Test
    void testAddUserWithoutCards() {
        User user = new User(1, "TestName", "TestSurname", "TestEmail", 100, "pwd");
        when(cardRestConsumerMock.generateCardsForNewUser(user.getIdUser())).thenReturn(ResponseEntity.ok(listWithoutCards));
        Assertions.assertThat(userService.addUser(user)).isFalse();
    }

    @Test
    void testAddUserWithException() {
        User user = new User(1, "TestName", "TestSurname", "TestEmail", 100, "pwd");
        when(cardRestConsumerMock.generateCardsForNewUser(user.getIdUser())).thenThrow(RuntimeException.class);
        Assertions.assertThat(userService.addUser(user)).isFalse();
    }

    //=========== isValidUserRegistration ===========

    @Test
    void testIsValidUserRegistrationUserAlreadyPresent() {
        User user = new User(1, "TestName", "TestSurname", "TestEmail", 100, "pwd");
        doReturn(true).when(userService).isInDatabase(user);
        Assertions.assertThat(userService.isValidUserRegistration(user)).isFalse();
    }

    @Test
    void testIsValidUserRegistrationUserWithoutName() {
        User user = new User(1, null, "TestSurname", "TestEmail", 100, "pwd");
        doReturn(false).when(userService).isInDatabase(user);
        Assertions.assertThat(userService.isValidUserRegistration(user)).isFalse();
    }

    @Test
    void testIsValidUserRegistrationUserWithoutSurname() {
        User user = new User(1, "TestName", "", "TestEmail", 100, "pwd");
        doReturn(true).when(userService).isInDatabase(user);
        Assertions.assertThat(userService.isValidUserRegistration(user)).isFalse();
    }

    @Test
    void testIsValidUserRegistration() {
        User user = new User(1, "TestName", "TestSurname", "TestEmail", 100, "pwd");
        doReturn(true).when(userService).isInDatabase(user);
        Assertions.assertThat(userService.isValidUserRegistration(user)).isFalse();
    }

    //=========== changeMoneyOfUser ===========
    @Test
    void testChangeMoneyOfUserWithNoMoney() {
        User user = new User(1, "TestName", "TestSurname", "TestEmail", 0, "pwd");
        Assertions.assertThat(userService.changeMoneyOfUser(user,-100)).isFalse();
    }

    @Test
    void testChangeMoneyOfUserWithExeception() {
        User user = new User(1, "TestName", "TestSurname", "TestEmail", 200, "pwd");
        when(userRepositoryMock.save(user)).thenThrow(RuntimeException.class);
        Assertions.assertThat(userService.changeMoneyOfUser(user,-100)).isFalse();
    }

}

