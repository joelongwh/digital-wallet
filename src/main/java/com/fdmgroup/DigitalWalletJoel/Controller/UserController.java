package com.fdmgroup.DigitalWalletJoel.Controller;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdmgroup.DigitalWalletJoel.Model.User;
import com.fdmgroup.DigitalWalletJoel.Model.Wallet;
import com.fdmgroup.DigitalWalletJoel.Service.UserService;

/**
 * @author joelongwh
 * UserController Class to get and post mappings for pages related to users
 * Associated with UserService Class
 */
@Controller
@RequestMapping("user")
public class UserController {
	
	private static final Logger LOGGER = LogManager.getLogger(UserController.class);
	
	private UserService userService;

	/**
	 * Constructor for UserController
	 * @param userService
	 */
	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	/**
	 * Gets mapping for home page
	 * @param auth
	 * @param session
	 * @return home.html
	 */
	@GetMapping("login")
	public String verifyUser(Authentication auth, HttpSession session) {
		LOGGER.info("Get mapping for login page");
		String username = auth.getName();
		User user = this.userService.findByUsername(username);
		Wallet wallet = user.getWallet();
		session.setAttribute("wallet", wallet);
		return "home.html";
	}
	
	/**
	 * Gets mapping for view user page
	 * @param auth
	 * @param model
	 * @return viewUser.html
	 */
	@GetMapping("viewuser")
	public String viewUser(Authentication auth, Model model) {
		LOGGER.info("Get mapping for view user page");
		String username = auth.getName();
		User user = this.userService.findByUsername(username);
		model.addAttribute("user", user);
		return "viewUser.html";
	}
	
	/**
	 * Gets mapping for edit user page
	 * @param auth
	 * @param model
	 * @return editUserForm.html
	 */
	@GetMapping("edituser")
	public String editUser(Authentication auth, Model model) {
		LOGGER.info("Get mapping for edit user page");
		String username = auth.getName();
		User user = this.userService.findByUsername(username);
		model.addAttribute("user", user);
		return "editUserForm.html";
	}
	
	/**
	 * Posts mapping for edit user page
	 * @param user
	 * @param auth
	 * @return redirect:/logout or editUserForm.html
	 */
	@PostMapping("edituser/save")
	public String processEditUser(@ModelAttribute User user, Authentication auth) {
		LOGGER.info("Post mapping for edit user page");
		if (this.userService.findByUsername(user.getUsername()) == null) {
			this.userService.createNewUser(user);
			return "redirect:/logout";
		}
		LOGGER.warn("Username unavailable");
		return "editUserForm.html";
	}
	
	/**
	 * Gets mapping for new user page
	 * @param model
	 * @return newUserForm.html
	 */
	@GetMapping("newuser")
	public String getNewUser(Model model) {
		LOGGER.info("Get mapping for new user page");
		model.addAttribute("user", new User());
		return "newUserForm.html";
	}
	
	/**
	 * Posts mapping for new user page
	 * @param username
	 * @param password
	 * @param confirmPassword
	 * @return redirect:/login or newUserForm.html
	 */
	@PostMapping("newuser")
	public String processNewUser(@RequestParam String username, @RequestParam String password, @RequestParam String confirmPassword) {
		LOGGER.info("Post mapping for new user page");
		if (password.equals(confirmPassword) && this.userService.findByUsername(username) == null) {
			LOGGER.info("Passwords match and username available");
			User user = new User(username, password, new Wallet());
			this.userService.createNewUser(user);
			return "redirect:/login";
		}
		LOGGER.warn("Passwords do not match or username unavailable");
		return "newUserForm.html";
	}
	
	/**
	 * Gets mapping for delete user page
	 * @param auth
	 * @return redirect:/logout
	 */
	@GetMapping("deleteuser")
	public String deleteUser(Authentication auth) {
		LOGGER.info("Get mapping for delete user page");
		String username = auth.getName();
		User user = this.userService.findByUsername(username);
		userService.deleteById(user.getId());
		return "redirect:/logout";
	}

}
