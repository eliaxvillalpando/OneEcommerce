package com.iudc.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.iudc.admin.user.UserRepository;
import com.iudc.common.entity.User;

public class iudcUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.getUserByEmail(email);
		if (user != null) {
			return new iudcUserDetails(user);
		}
		
		throw new UsernameNotFoundException("No se pudo encontrar un usuario con email: " + email);
	}

}
