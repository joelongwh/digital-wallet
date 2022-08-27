package com.fdmgroup.DigitalWalletJoel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fdmgroup.DigitalWalletJoel.Model.User;
import com.fdmgroup.DigitalWalletJoel.Model.Wallet;
import com.fdmgroup.DigitalWalletJoel.Repository.UserRepository;
import com.fdmgroup.DigitalWalletJoel.Service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@Mock
	private UserRepository userRepoMock;
	
	@Mock
	private PasswordEncoder encoder;
	
	@Mock
	private User userMock;
	
	private UserService userService;
	
	@BeforeEach
	public void setup() {
		userService = new UserService(userRepoMock, encoder);
	}
	
	@Test
	public void test_that_createNewUser_encodesPassword_andSavesUser() {
		//Arrange
		when(userMock.getPassword()).thenReturn("password");
		when(encoder.encode("password")).thenReturn("encodedPassword");
		
		//Act
		userService.createNewUser(userMock);
		
		//Assert
		verify(userMock,times(1)).setPassword("encodedPassword");
		verify(userRepoMock,times(1)).save(userMock);
	}
	
	@Test
	public void test_that_calls_userRepoMock() {
		//Arrange
		
		//Act
		userService.save(userMock);
		
		//Assert
		verify(userRepoMock,times(1)).save(userMock);
	}
	
	@Test
	public void test_that_existsByUsernameAndPassword_calls_userRepoMock() {
		//Arrange
		
		//Act
		userService.existsByUsernameAndPassword("username", "password");
		
		//Assert
		verify(userRepoMock,times(1)).existsByUsernameAndPassword("username", "password");
	}
	
	@Test
	public void test_that_findByUsername_calls_userRepoMock() {
		//Arrange
		
		//Act
		userService.findByUsername("username");
		
		//Assert
		verify(userRepoMock,times(1)).findByUsername("username");
	}
	
	@Test
	public void test_that_findById_returnsUser_whenValid() {
		//Arrange
		User expected = new User("username", "password", new Wallet());
		Long id = 1l;
		when(userRepoMock.findById(id)).thenReturn(Optional.of(expected));
		
		//Act
		User actual = userService.findById(id);
		
		//Assert
		assertEquals(actual,expected);
		verify(userRepoMock,times(1)).findById(id);
	}
	
	@Test
	public void test_that_findById_returnsNull_whenInvalid() {
		//Arrange
		Long id = 1l;
		when(userRepoMock.findById(id)).thenReturn(Optional.ofNullable(null));
				
		//Act
		User actual = userService.findById(id);
				
		//Assert
		assertNull(actual);
		verify(userRepoMock,times(1)).findById(id);
	}
	
	@Test
	public void test_that_findByPassword_calls_userRepoMock() {
		//Arrange
		
		//Act
		userService.findByPassword("password");
		
		//Assert
		verify(userRepoMock,times(1)).findByPassword("password");
	}
	
	@Test
	public void test_that_deleteById_calls_userRepoMock() {
		//Arrange
		
		//Act
		userService.deleteById(1l);
		
		//Assert
		verify(userRepoMock,times(1)).deleteById(1l);
	}

}
