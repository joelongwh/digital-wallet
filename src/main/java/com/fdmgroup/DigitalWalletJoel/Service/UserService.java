package com.fdmgroup.DigitalWalletJoel.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fdmgroup.DigitalWalletJoel.Model.User;
import com.fdmgroup.DigitalWalletJoel.Repository.UserRepository;

/**
 * @author joelongwh
 * UserService class to abstract functionality from UserController class
 * Associated with UserRepository class and PasswordEncoder
 */
@Service
public class UserService {
	
	private UserRepository userRepo;
	private PasswordEncoder encoder;

	/**
	 * Constructor for UserService
	 * @param userRepo
	 * @param encoder
	 */
	@Autowired
	public UserService(UserRepository userRepo, PasswordEncoder encoder) {
		super();
		this.userRepo = userRepo;
		this.encoder = encoder;
	}
	
	/**
	 * Method to create new user and encode the password
	 * @param user
	 */
	public void createNewUser(User user) {
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		this.userRepo.save(user);
	}

	/**
	 * Method to save user
	 * @param user
	 */
	public void save(User user) {
		this.userRepo.save(user);
	}
	
	/**
	 * Method to check if user exists by username and password
	 * @param username
	 * @param password
	 * @return true if yes, false if no
	 */
	public boolean existsByUsernameAndPassword(String username, String password) {
		return this.userRepo.existsByUsernameAndPassword(username, password);
	}

	/**
	 * Method to find user by username
	 * @param username
	 * @return user with corresponding username
	 */
	public User findByUsername(String username) {
		return this.userRepo.findByUsername(username);
	}

	/**
	 * Method to find user by id
	 * @param userId
	 * @return user with corresponding id
	 */
	public User findById(long userId) {
		return this.userRepo.findById(userId).orElse(null);
	}
	
	/**
	 * Method to find user by password
	 * @param password
	 * @return user with corresponding password
	 */
	public User findByPassword(String password) {
		return this.userRepo.findByPassword(password);
	}

	/**
	 * Method to delete user by id
	 * @param id
	 */
	public void deleteById(long id) {
		this.userRepo.deleteById(id);
	}
	
	

}
