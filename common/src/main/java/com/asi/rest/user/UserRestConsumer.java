package com.asi.rest.user;

import com.asi.dto.user.BalanceUserDto;
import com.asi.dto.user.UserDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class UserRestConsumer implements IUserRest {

    @Autowired
	private RestTemplate restTemplate;

    @Override
    public ResponseEntity<UserDto> getUserProfile() {
        return restTemplate.getForEntity(PROFILE, UserDto.class);
    }

    @Override
    public ResponseEntity<String> balanceUserMoney(BalanceUserDto userDto) {
        return restTemplate.postForEntity(BALANCE, userDto, String.class);
    }
}
