package com.fdmgroup.DigitalWalletJoel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import com.fdmgroup.DigitalWalletJoel.Controller.UserController;
import com.fdmgroup.DigitalWalletJoel.Model.User;
import com.fdmgroup.DigitalWalletJoel.Model.Wallet;
import com.fdmgroup.DigitalWalletJoel.Service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
	
	@Mock
	private UserService userServiceMock;
	
	@Mock
	private Model modelMock;
	
	@Mock
	private Authentication authMock;
	
	@Mock
	private HttpSession sessionMock;
	
	@Mock
	private User userMock;
	
	private UserController userController;
	
	@BeforeEach
	private void setup() {
		userController = new UserController(userServiceMock);
	}
	
	@Test
	public void test_that_login_setsSessionAttribute_returnsHomeHtml() {
		//Arrange
		String expected = "home.html";
		String username = "username";
		Wallet wallet = new Wallet();
		when(authMock.getName()).thenReturn(username);
		when(userServiceMock.findByUsername(username)).thenReturn(userMock);
		when(userMock.getWallet()).thenReturn(wallet);
		
		//Act
		String actual = userController.verifyUser(authMock, sessionMock);
		
		//Assert
		assertEquals(expected, actual);
		verify(sessionMock,times(1)).setAttribute("wallet", wallet);
	}
	
	@Test
	public void test_that_viewUser_addsModelAttribute_returnsViewUserHtml() {
		//Arrange
		String expected = "viewUser.html";
		String username = "username";
		when(authMock.getName()).thenReturn(username);
		when(userServiceMock.findByUsername(username)).thenReturn(userMock);
		
		//Act
		String actual = userController.viewUser(authMock, modelMock);
		
		//Assert
		assertEquals(expected, actual);
		verify(modelMock,times(1)).addAttribute("user", userMock);
	}
	
	@Test
	public void test_that_editUser_addsModelAttribute_returnsEditUserFormHtml() {
		//Arrange
		String expected = "editUserForm.html";
		String username = "username";
		when(authMock.getName()).thenReturn(username);
		when(userServiceMock.findByUsername(username)).thenReturn(userMock);
		
		//Act
		String actual = userController.editUser(authMock, modelMock);
		
		//Assert
		assertEquals(expected, actual);
		verify(modelMock,times(1)).addAttribute("user", userMock);
	}
	
	@Test
	public void test_that_processEditUser_returnsEditUserFormHtml_ifInvalid() {
		//Arrange
		String expected = "editUserForm.html";
		when(userServiceMock.findByUsername(userMock.getUsername())).thenReturn(new User());
		
		//Act
		String actual = userController.processEditUser(userMock, authMock);
		
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_that_processEditUser_editsUser_returnsRedirectLogout_ifValid() {
		//Arrange
		String expected = "redirect:/logout";
		when(userServiceMock.findByUsername(userMock.getUsername())).thenReturn(null);
		
		//Act
		String actual = userController.processEditUser(userMock, authMock);
		
		//Assert
		assertEquals(expected, actual);
		verify(userServiceMock,times(1)).createNewUser(userMock);
	}
	
	@Test
	public void test_that_getNewUser_addsModelAttribute_returnsNewUserFormHtml() {
		//Arrange
		String expected = "newUserForm.html";
		
		//Act
		String actual = userController.getNewUser(modelMock);
		
		//Assert
		assertEquals(expected, actual);
		verify(modelMock,times(1)).addAttribute("user", new User());
	}
	
	@Test
	public void test_that_processNewUser_doesNotCreateNewUser_ifPasswordsDoNotMatch() {
		//Arrange
		String expected = "newUserForm.html";
		String username = "username";
		String password = "password";
		String confirmPassword = "differentPassword";
		
		//Act
		String actual = userController.processNewUser(username, password, confirmPassword);
		
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_that_processNewUser_doesNotCreateNewUser_ifUsernameExists() {
		//Arrange
		String expected = "newUserForm.html";
		String username = "username";
		String password = "password";
		String confirmPassword = "password";
		when(userServiceMock.findByUsername(username)).thenReturn(userMock);
		
		//Act
		String actual = userController.processNewUser(username, password, confirmPassword);
		
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_that_processNewUser_createsNewUser_ifValid() {
		//Arrange
		String expected = "redirect:/login";
		String username = "username";
		String password = "password";
		String confirmPassword = "password";
		User user = new User(username, password, new Wallet());
		
		//Act
		String actual = userController.processNewUser(username, password, confirmPassword);
		
		//Assert
		assertEquals(expected, actual);
		verify(userServiceMock,times(1)).createNewUser(user);
	}
	
	@Test
	public void test_that_deleteUser_deletesUser_andReturnsRedirectLogout() {
		//Arrange
		String expected = "redirect:/logout";
		when(authMock.getName()).thenReturn("username");
		when(userServiceMock.findByUsername("username")).thenReturn(userMock);
		
		//Act
		String actual = userController.deleteUser(authMock);
		
		//Assert
		assertEquals(expected,actual);
		verify(userServiceMock,times(1)).deleteById(userMock.getId());
	}

}
