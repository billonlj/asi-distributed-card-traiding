package com.asi.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.asi.model.User;
import com.asi.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	private HttpServletRequest request;
	
	public Boolean addUser(User user) {
		user.setMoneyUser(1000.0);
		userRepository.save(user);
		System.out.println("User created : " + user.getEmailUser());
		
		// get five card 
		//cardInstanceService.giveCardsToNewUser(user);
		System.out.println("TODO: Give card to user !");
		URL url;
		try {
			url = new URL("http://localhost:8080/api/cards/users/register/" + user.getIdUser());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("accept", "application/json");
			InputStream responseStream = connection.getInputStream();
			
			if (true) { //responseStream) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isInDatabase(User user) {
		Optional<User> userFind = userRepository.findByEmailUser(user.getEmailUser());
		return userFind.isPresent();
	}
	
	public boolean isValidUserRegistration(User user) {
		boolean isValid = true;
		if(!this.isInDatabase(user)) {
		
			if(user.getNameUser() == null || user.getNameUser().isEmpty()) {
				isValid = false;
			}
			
			if(user.getSurnameUser() == null || user.getSurnameUser().isEmpty()) {
				isValid = false;
			}
		} else {
			isValid = false;
		}
		return isValid;
	}
	
	public String login(User user, String password) {
		if(BCrypt.checkpw(password, user.getPasswordUser())) {
			return createTokenFromUser(user);
		} else {
			return null;
		}
    }
	
	public User getUserByEmail(String email) {
		Optional<User> user = userRepository.findByEmailUser(email);
		if(user.isPresent()) {
			return user.get();
		} else {
			return null;
		}
	}
	
	public User getRequestUser() {
		String authToken = request.getHeader("Authorization");
		
		if(authToken == null || authToken.isEmpty()) {			
			return null;
		}
		
		String email = Jwts.parser()
				.setSigningKey(TextCodec.BASE64.decode("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E="))
				.parseClaimsJws(authToken)
				.getBody()
				.getSubject();
		
		return getUserByEmail(email);
	}
	
	public User getUserById(int id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return user.get();
		} else {
			return null;
		}
	}
	
	public Boolean changeMoneyOfUser(User user,double balancedMoney) {
		try {
			double oldMoney = user.getMoneyUser();
			double newMoney = oldMoney + balancedMoney;
			if (newMoney >= 0) {
				user.setMoneyUser(newMoney);
				userRepository.save(user);
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}
	}
	
	private String createTokenFromUser(User user) {
		return Jwts.builder()
			  .setIssuer("CardTrading")
			  .setSubject(user.getEmailUser())
			  .claim("fullName", user.getNameUser() + " " + user.getSurnameUser())
			  .claim("scope", "user")
			  .setIssuedAt(Date.from(Instant.ofEpochSecond(1466796822L)))
			  .setExpiration(Date.from(Instant.ofEpochSecond(4622470422L)))
			  .signWith(
			    SignatureAlgorithm.HS256,
			    TextCodec.BASE64.decode("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=")
			  )
			  .compact();
	}
}