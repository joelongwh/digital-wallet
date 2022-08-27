package com.fdmgroup.DigitalWalletJoel.Controller;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fdmgroup.DigitalWalletJoel.Model.Wallet;
import com.fdmgroup.DigitalWalletJoel.Service.TransactionService;

/**
 * @author joelongwh
 * TransactionController class to get mappings for pages related to transactions
 * Associated with TransactionService Class
 */
@Controller
@RequestMapping("transaction")
public class TransactionController {
	
	private static final Logger LOGGER = LogManager.getLogger(TransactionController.class);
	
	private TransactionService transactionService;

	/**
	 * Constructor for TransactionController
	 * @param transactionService
	 */
	@Autowired
	public TransactionController(TransactionService transactionService) {
		super();
		this.transactionService = transactionService;
	}
	
	/**
	 * Gets mapping for view transactions page
	 * @param session
	 * @param model
	 * @return viewTransactions.html
	 */
	@GetMapping("viewtransactions")
	public String viewTransactionHistory(HttpSession session, Model model) {
		LOGGER.info("Get mapping for view transactions page");
		Wallet wallet = (Wallet) session.getAttribute("wallet");
		model.addAttribute("transactions", this.transactionService.findByWallet(wallet));
		return "viewTransactions.html";
	}

}
