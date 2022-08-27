package com.fdmgroup.DigitalWalletJoel.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.DigitalWalletJoel.Model.User;

/**
 * @author joelongwh
 * UserRepository interface that extends JpaRepository to access JPA methods
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	boolean existsByUsernameAndPassword(String username, String password);

	User findByUsername(String username);

	User findByPassword(String password);

}
