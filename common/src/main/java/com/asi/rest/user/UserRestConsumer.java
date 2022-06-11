package com.asi.rest.user;

import com.asi.dto.user.BalanceUserDto;
import com.asi.dto.user.UserDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class UserRestConsumer implements IUserRest {

	private RestTemplate restTemplate = new RestTemplate();;

    @Override
    public ResponseEntity<UserDto> getUserProfile() {
        return restTemplate.getForEntity(BASE_PATH + PROFILE, UserDto.class);
    }

    @Override
    public ResponseEntity<Boolean> balanceUserMoney(BalanceUserDto userDto) {
        System.out.println("Consumer_______________________________\n");
		System.out.println(restTemplate + BASE_PATH + BALANCE + userDto + Boolean.class);
		System.out.println("Consumer_______________________________\n");
        return restTemplate.postForEntity(BASE_PATH + BALANCE, userDto, Boolean.class);
    }
}
