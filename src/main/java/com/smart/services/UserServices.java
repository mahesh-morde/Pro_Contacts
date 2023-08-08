package com.smart.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.smart.DTOs.AddUserDTO;
import com.smart.entities.User;
import com.smart.repositories.UserRepository;

@Service
public class UserServices {
	
	@Autowired
	private UserRepository userrepo;
	
	@Autowired
	private BCryptPasswordEncoder passencoder;
	
	public User register(AddUserDTO addUserDTO) {
		
		if(this.userrepo.findByEmail(addUserDTO.getEmail()).isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User Already Exist");
		}
		
		User user = new User();
		
		user.setName(addUserDTO.getName());
		user.setEmail(addUserDTO.getEmail());
		user.setPassward(passencoder.encode(addUserDTO.getPassward()));
		
		System.out.println(user);
		
		
		this.userrepo.save(user);
		
		return user;
	}
}
