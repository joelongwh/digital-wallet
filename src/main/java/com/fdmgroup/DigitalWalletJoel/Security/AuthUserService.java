package com.fdmgroup.DigitalWalletJoel.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fdmgroup.DigitalWalletJoel.Service.UserService;

/**
 * @author joelongwh
 * AuthUserService class that implements UserDetailsService to load user by username
 */
@Service
public class AuthUserService implements UserDetailsService {
	
	private UserService userService;

	@Autowired
	public AuthUserService(UserService userService) {
		super();
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return new AuthUserDetails(userService.findByUsername(username));
	}
	
	

}
