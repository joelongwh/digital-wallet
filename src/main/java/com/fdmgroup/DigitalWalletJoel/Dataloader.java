package com.fdmgroup.DigitalWalletJoel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fdmgroup.DigitalWalletJoel.Model.User;
import com.fdmgroup.DigitalWalletJoel.Model.Wallet;
import com.fdmgroup.DigitalWalletJoel.Service.UserService;

/**
 * @author joelongwh
 * Dataloader class to prepopulate mySQL database with two users
 * Associated with UserService class
 */
@Component
public class Dataloader implements ApplicationRunner {
	
	private UserService userService;
	
	@Autowired
	public Dataloader(UserService userService) {
		super();
		this.userService = userService;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		User user1 = new User("u1", "p1", new Wallet());
		User user2 = new User("u2", "p2", new Wallet());
		
		userService.createNewUser(user1);
		userService.createNewUser(user2);

	}

	

}
