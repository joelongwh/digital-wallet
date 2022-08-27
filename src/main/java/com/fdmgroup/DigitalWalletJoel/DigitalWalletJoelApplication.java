package com.fdmgroup.DigitalWalletJoel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author joelongwh
 * Class where Digital Wallet Spring Application is run
 */
@SpringBootApplication
public class DigitalWalletJoelApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalWalletJoelApplication.class, args);
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println(new BCryptPasswordEncoder().encode("password"));
        return new BCryptPasswordEncoder();
    }

}
