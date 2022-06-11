package com.asi.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.asi.dto.user.BalanceUserDto;
import com.asi.dto.user.LoginUserDto;
import com.asi.dto.user.RegisterUserDto;
import com.asi.dto.user.UserDto;
import com.asi.model.User;
import com.asi.rest.user.IUserRest;
import com.asi.service.UserService;

@RestController
public class UserController implements IUserRest {
	@Autowired
	UserService userService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@RequestMapping("/test")
	@ResponseBody
	public ResponseEntity<?> test() {
		
		return new ResponseEntity<String>("YESSSS", HttpStatus.OK);

	}
	
	@Override
	@ResponseBody
	public ResponseEntity<UserDto> getUserProfile() {
		User currentUser = userService.getRequestUser();
		if(currentUser != null) {			
			UserDto profilUserDto = modelMapper.map(currentUser, UserDto.class);
			return new ResponseEntity<UserDto>(profilUserDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<UserDto>(new UserDto(), HttpStatus.BAD_REQUEST);
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
	
	@Override
	@ResponseBody
	public ResponseEntity<Boolean> balanceUserMoney(@RequestBody BalanceUserDto userDto) {
		User user = userService.getUserById(userDto.getIdUser());
		
		Boolean isMoneyChange = userService.changeMoneyOfUser(user, userDto.getBalanceMoney());
		
	    if (isMoneyChange) {
	    	return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	    } else {
	    	return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
	    }
	}
	
	
	private User convertToEntity(RegisterUserDto registerUserDto) {
		User user = modelMapper.map(registerUserDto, User.class);
		return user;
	}
}
