package com.fdmgroup.DigitalWalletJoel.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.DigitalWalletJoel.Model.Transaction;
import com.fdmgroup.DigitalWalletJoel.Model.Wallet;
import com.fdmgroup.DigitalWalletJoel.Repository.WalletRepository;

/**
 * @author joelongwh
 * WalletService class to abstract functionality from WalletController class
 * Associated with WalletRepository class
 */
@Service
public class WalletService {
	
	private WalletRepository walletRepo;

	/**
	 * Constructor for WalletService
	 * @param walletRepo
	 */
	@Autowired
	public WalletService(WalletRepository walletRepo) {
		super();
		this.walletRepo = walletRepo;
	}
	
	/**
	 * Method to find wallet by userId
	 * @param userId
	 * @return wallet with corresponding userId
	 */
	public Wallet findById(long userId) {
		return this.walletRepo.findById(userId).orElse(null);
	}
	
	/**
	 * Method to find wallet by wallet number
	 * @param walletNumber
	 * @return wallet with corresponding wallet number
	 */
	public Wallet findByWalletNumber(long walletNumber) {
		return this.walletRepo.findByWalletNumber(walletNumber);
	}
	
	/**
	 * Method to increase wallet balance
	 * @param walletId
	 * @param topUpAmount
	 * @param transactionDescription
	 * @return wallet with updated balance
	 */
	public Wallet topUpWallet(long walletId, double topUpAmount, String transactionDescription) {
		Wallet wallet = this.walletRepo.findById(walletId).orElse(null);
		wallet.setWalletBalance(wallet.getWalletBalance() + topUpAmount);
		Transaction transaction = new Transaction(java.time.LocalDate.now(), topUpAmount, transactionDescription, wallet);
		wallet.addTransaction(transaction);
		this.walletRepo.save(wallet);
		return wallet;
	}

	/**
	 * Method to decrease wallet balance
	 * @param walletId
	 * @param withdrawalAmount
	 * @param transactionDescription
	 * @return wallet with updated balance
	 */
	public Wallet withdrawFromWallet(long walletId, double withdrawalAmount, String transactionDescription) {
		Wallet wallet = this.walletRepo.findById(walletId).orElse(null);
		wallet.setWalletBalance(wallet.getWalletBalance() - withdrawalAmount);
		Transaction transaction = new Transaction(java.time.LocalDate.now(), withdrawalAmount, transactionDescription, wallet);
		wallet.addTransaction(transaction);
		this.walletRepo.save(wallet);
		return wallet;
	}

	

}
