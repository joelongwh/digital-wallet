package com.fdmgroup.DigitalWalletJoel.Controller;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdmgroup.DigitalWalletJoel.Model.Wallet;
import com.fdmgroup.DigitalWalletJoel.Service.WalletService;

/**
 * @author joelongwh
 * WalletController Class to get and post mappings for pages related to wallets
 */
@Controller
@RequestMapping("wallet")
public class WalletController {
	
	private static final Logger LOGGER = LogManager.getLogger(WalletController.class);
	
	private WalletService walletService;

	/**
	 * Constructor for WalletController
	 * @param walletService
	 */
	@Autowired
	public WalletController(WalletService walletService) {
		super();
		this.walletService = walletService;
	}
	
	/**
	 * Gets mapping for top up page
	 * @return topUpForm.html
	 */
	@GetMapping("topup")
	public String topUpWallet() {
		LOGGER.info("Get mapping for top up wallet page");
		return "topUpForm.html";
	}
	
	/**
	 * Posts mapping for top up page
	 * @param session
	 * @param topUpAmount
	 * @return redirect:/home or withdrawalForm.html
	 */
	@PostMapping("topup/submit")
	public String processTopUp(HttpSession session, @RequestParam double topUpAmount) {
		LOGGER.info("Post mapping for top up wallet page");
		Wallet wallet = (Wallet) session.getAttribute("wallet");
		long walletId = wallet.getId();
		String transactionDescription = "Credit - Top Up";
		wallet = this.walletService.topUpWallet(walletId, topUpAmount, transactionDescription);
		session.setAttribute("wallet", wallet);
		return "redirect:/home";
	}
	
	/**
	 * Gets mapping for withdrawal page
	 * @return withdrawalForm.html
	 */
	@GetMapping("withdraw")
	public String withdrawFromWallet() {
		LOGGER.info("Get mapping for withdraw from wallet page");
		return "withdrawalForm.html";
	}
	
	/**
	 * Posts mapping for withdrawal page
	 * @param session
	 * @param withdrawalAmount
	 * @return redirect:/home or withdrawalForm.html
	 */
	@PostMapping("withdraw/submit")
	public String processWithdrawal(HttpSession session, @RequestParam double withdrawalAmount) {
		LOGGER.info("Post mapping for withdraw from wallet page");
		Wallet wallet = (Wallet) session.getAttribute("wallet");
		long walletId = wallet.getId();
		double currentBalance = wallet.getWalletBalance();
		if (withdrawalAmount < currentBalance) {
			LOGGER.info("Sufficient balance for withdrawal");
			String transactionDescription = "Debit - Withdrawal";
			wallet = this.walletService.withdrawFromWallet(walletId, withdrawalAmount, transactionDescription);
			session.setAttribute("wallet", wallet);
			return "redirect:/home";
		}
		LOGGER.warn("Insufficient balance for withdrawal");
		return "withdrawalForm.html";
	}
	
	/**
	 * Gets mapping for transfer page
	 * @return transferForm.html
	 */
	@GetMapping("transfer")
	public String transfer() {
		LOGGER.info("Get mapping for transfer page");
		return "transferForm.html";
	}
	
	/**
	 * Posts mapping for transfer page
	 * @param session
	 * @param recipientWalletNumber
	 * @param transferAmount
	 * @return redirect:/home or transferForm.html
	 */
	@PostMapping("transfer/submit")
	public String processTransfer(HttpSession session, @RequestParam long recipientWalletNumber, @RequestParam double transferAmount) {
		LOGGER.info("Post mapping for transfer page");
		Wallet fromWallet = (Wallet) session.getAttribute("wallet");
		double currentBalance = fromWallet.getWalletBalance();
		Wallet toWallet = this.walletService.findByWalletNumber(recipientWalletNumber);
		if (transferAmount < currentBalance && toWallet != null && fromWallet.getId() != toWallet.getId()) {
			LOGGER.info("Sufficient balance for transfer, recipient wallet exists and the transferrer and transferee are different");
			String fromTransactionDescription = "Debit - Transferred to " + toWallet.getUser().getUsername();
			fromWallet = this.walletService.withdrawFromWallet(fromWallet.getId(), transferAmount, fromTransactionDescription);
			String toTransactionDescription = "Credit - Received from " + fromWallet.getUser().getUsername();
			toWallet = this.walletService.topUpWallet(toWallet.getId(), transferAmount, toTransactionDescription);
			session.setAttribute("wallet", fromWallet);
			return "redirect:/home";
		}
		LOGGER.warn("Insufficient balance for transfer, recipient wallet does not exist or transferee is the transferrer");
		return "transferForm.html";
	}
	
	

}
