package com.asi.controller;

import com.asi.model.User;
import com.asi.service.UserService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class UserControllerTest {
    
    @InjectMocks
    UserService userController;

    @Mock
	UserService userServiceMock;

    @Test
	public void testFindTheGreatestFromAllData() {
        User user = new User(1, "TestName", "TestSurname", "TestEmail", 100, "pwd");
		when(userServiceMock.addUser(user)).thenReturn(true);
		assertEquals(24, userController.addUser(user));
	}


}
