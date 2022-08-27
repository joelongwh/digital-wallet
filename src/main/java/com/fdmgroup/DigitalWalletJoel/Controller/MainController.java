package com.fdmgroup.DigitalWalletJoel.Controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author joelongwh
 * MainController class to get mappings for landing and home pages of application
 */
@Controller
public class MainController {
	
	private static final Logger LOGGER = LogManager.getLogger(MainController.class);
	
	/**
	 * Gets mapping for landing page
	 * @return landing.html page
	 */
	@GetMapping
	public String getLanding() {
		LOGGER.info("Get mapping for landing page");
		return "landing.html";
	}
	
	/**
	 * Gets mapping for home page
	 * @return home.html page
	 */
	@GetMapping("home")
	public String getHome() {
		LOGGER.info("Get mapping for home page");
		return "home.html";
	}
	
	

}
