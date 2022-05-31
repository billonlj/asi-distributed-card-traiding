package com.asi.rest.user;

import com.asi.dto.user.UserDto;
import com.asi.dto.user.BalanceUserDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface IUserRest {
    public final String ROOT_PATH = "/api/users";

	public final String PROFILE = ROOT_PATH + "/profile";
	public final String BALANCE = ROOT_PATH + "/balance";

    
    @RequestMapping(PROFILE)
    public ResponseEntity<UserDto> getUserProfile();

    @RequestMapping(value = BALANCE, method=RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> balanceUserMoney(@RequestBody BalanceUserDto userDto);
}
