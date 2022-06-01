package com.asi.service;



import com.asi.dto.CardInstanceDto;
import com.asi.model.User;
import com.asi.repository.UserRepository;
import com.asi.rest.card.CardRestConsumer;
import com.asi.service.UserService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito.when;
import org.springframework.http.ResponseEntity;

public class UserServiceTest {
    
    @InjectMocks
    UserService userService;

    @Mock
	UserRepository userRepositoryMock;

    @Mock
    CardRestConsumer cardRestConsumer;

    private CardInstanceDto[] arrayWithCards = new CardInstanceDto[]{
        new CardInstanceDto(1,1,1,1,1,1,1),
        new CardInstanceDto(2,1,1,1,1,1,1),
        new CardInstanceDto(3,1,1,1,1,1,1)
    };

    private CardInstanceDto[] arrayWithoutCards = new CardInstanceDto[]{};

    // =========== addUser ===========
    @Test
	public void testAddUserWithCards() {
        User user = new User(1, "TestName", "TestSurname", "TestEmail", 100, "pwd");
		when(cardRestConsumer.generateCardsForNewUser(user.getIdUser())).thenReturn(ResponseEntity.of(arrayWithCards));
		assertEquals(true, userService.addUser(user));
	}

    @Test
	public void testAddUserWithoutCards() {
        User user = new User(1, "TestName", "TestSurname", "TestEmail", 100, "pwd");
		when(cardRestConsumer.generateCardsForNewUser(user.getIdUser()).getBody()).ThenReturn(this.arrayWithoutCards);
		assertEquals(false, userService.addUser(user));
	}

    @Test
	public void testAddUserWithExecptionCards() {
        User user = new User(1, "TestName", "TestSurname", "TestEmail", 100, "pwd");
		when(cardRestConsumer.generateCardsForNewUser(user.getIdUser()).getBody()).thenThrow(NullPointerException.class);
		assertEquals(false, userService.addUser(user));
	}



}

