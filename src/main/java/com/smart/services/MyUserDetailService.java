package com.smart.services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smart.entities.User;
import com.smart.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class MyUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepository repository;
	
	@Override
    @Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.repository.findByEmail(username)
				.map(user  -> {
					return new User(
							user.getEmail(),
							user.getPassward(),
							user.getRoles().stream()
							.map(role -> new SimpleGrantedAuthority(role))
							.collect(Collectors.toList()));
				}).orElseThrow(() -> {
					throw new UsernameNotFoundException("User Not Found");
				}) ;
	}

}
