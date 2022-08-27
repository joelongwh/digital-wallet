package com.fdmgroup.DigitalWalletJoel.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.DigitalWalletJoel.Model.Transaction;
import com.fdmgroup.DigitalWalletJoel.Model.Wallet;
import com.fdmgroup.DigitalWalletJoel.Repository.TransactionRepository;

/**
 * @author joelongwh
 * TransactionService class to abstract functionality from TransactionController class
 * Associated with TransactionRepository class
 */
@Service
public class TransactionService {
	
	private TransactionRepository transactionRepo;

	/**
	 * Constructor for TransactionService
	 * @param transactionRepo
	 */
	@Autowired
	public TransactionService(TransactionRepository transactionRepo) {
		super();
		this.transactionRepo = transactionRepo;
	}

	/**
	 * Method to find transactions by wallet 
	 * @param wallet
	 * @return list of transactions associated with the wallet
	 */
	public List<Transaction> findByWallet(Wallet wallet) {
		return this.transactionRepo.findByWallet(wallet);
	}
	
	

}
