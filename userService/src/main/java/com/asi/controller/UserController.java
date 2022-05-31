package com.asi.controller;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.asi.dto.BalanceUserDto;
import com.asi.dto.LoginUserDto;
import com.asi.dto.RegisterUserDto;
import com.asi.dto.UserDto;
import com.asi.model.User;
import com.asi.service.UserService;

@RestController
public class UserController {
	@Autowired
	UserService userService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@RequestMapping("/test")
	@ResponseBody
	public ResponseEntity<?> test() {
		
		return new ResponseEntity<String>("YESSSS", HttpStatus.OK);

	}
	
	@RequestMapping("/api/users/profile")
	@ResponseBody
	public ResponseEntity<?> getUserProfile() {
		User currentUser = userService.getRequestUser();
		if(currentUser != null) {			
			UserDto profilUserDto = modelMapper.map(currentUser, UserDto.class);
			return new ResponseEntity<>(profilUserDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("No user connected", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/api/users/register", method=RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<?>  register(@RequestBody RegisterUserDto userDto) {
		//Create user 
		User user = convertToEntity(userDto);
		user.hashPassword();
		

		if (userService.isValidUserRegistration(user)) {
			// Send response
			Boolean isRegistred = userService.addUser(user);
			if (isRegistred) {
				return new ResponseEntity<>("User registred", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("An error has occured during creating cards for users", HttpStatus.BAD_REQUEST);
			}
			
		} else {
			return new ResponseEntity<String>("Bad request", HttpStatus.BAD_REQUEST);
		}		
	}
	
	@RequestMapping(value = "/api/users/login", method=RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> login(@RequestBody LoginUserDto userDto) {
		User user = userService.getUserByEmail(userDto.email);
		
		if(user != null) {			
			String token = userService.login(user, userDto.password);
			
			if(token != null) {
				UserDto profilUserDto = modelMapper.map(user, UserDto.class);
				profilUserDto.setToken(token);
				
				return new ResponseEntity<>(profilUserDto, HttpStatus.OK);
			}
		}
	    
	    return new ResponseEntity<String>("Bad request", HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/api/users/balance", method=RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> balanceUserMoney(@RequestBody BalanceUserDto userDto) {
		User user = userService.getUserById(userDto.getIdUser());
		
		Boolean isMoneyChange = userService.changeMoneyOfUser(user,userDto.getBalanceMoney());
		
	    if (isMoneyChange) {
	    	return new ResponseEntity<String>("Money has changed", HttpStatus.OK);
	    } else {
	    	return new ResponseEntity<String>("An error as occured :  the user's money has not changed", HttpStatus.BAD_REQUEST);
	    }
	}
	
	
	private User convertToEntity(RegisterUserDto registerUserDto) {
		User user = modelMapper.map(registerUserDto, User.class);
		return user;
	}
}
