package com.asi.service;


import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import com.asi.dto.CardInstanceDto;
import com.asi.model.User;
import com.asi.repository.UserRepository;
import com.asi.rest.card.CardRestConsumer;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepositoryMock;

    @Mock
    CardRestConsumer cardRestConsumer;

    private CardInstanceDto[] arrayWithCards = new CardInstanceDto[]{
        new CardInstanceDto(1, 1, 1, 1, 1, 1, 1),
        new CardInstanceDto(2, 1, 1, 1, 1, 1, 1),
        new CardInstanceDto(3, 1, 1, 1, 1, 1, 1)
    };

    private CardInstanceDto[] arrayWithoutCards = new CardInstanceDto[]{};

    // =========== addUser ===========
    @Test
    void testAddUserWithCards() {
        User user = new User(1, "TestName", "TestSurname", "TestEmail", 100, "pwd");
        when(cardRestConsumer.generateCardsForNewUser(user.getIdUser())).thenReturn(ResponseEntity.ok(arrayWithCards));
        Assertions.assertThat(userService.addUser(user)).isTrue();
    }

    @Test
    void testAddUserWithoutCards() {
        User user = new User(1, "TestName", "TestSurname", "TestEmail", 100, "pwd");
        when(cardRestConsumer.generateCardsForNewUser(user.getIdUser())).thenReturn(ResponseEntity.ok(arrayWithoutCards));
        Assertions.assertThat(userService.addUser(user)).isFalse();
    }

    @Test
    void testAddUserWithException() {
        User user = new User(1, "TestName", "TestSurname", "TestEmail", 100, "pwd");
        when(cardRestConsumer.generateCardsForNewUser(user.getIdUser())).thenThrow(RuntimeException.class);
        Assertions.assertThat(userService.addUser(user)).isFalse();
    }
}

