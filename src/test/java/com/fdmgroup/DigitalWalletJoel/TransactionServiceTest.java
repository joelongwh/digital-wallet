package com.fdmgroup.DigitalWalletJoel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.DigitalWalletJoel.Model.Transaction;
import com.fdmgroup.DigitalWalletJoel.Model.Wallet;
import com.fdmgroup.DigitalWalletJoel.Repository.TransactionRepository;
import com.fdmgroup.DigitalWalletJoel.Service.TransactionService;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
	
	@Mock
	private TransactionRepository transactionRepoMock;
	
	private TransactionService transactionService;
	
	@BeforeEach
	public void setup() {
		transactionService = new TransactionService(transactionRepoMock);
	}
	
	@Test
	public void test_that_findByWallet_returnsListOfTransactions() {
		//Arrange
		Wallet wallet1 = new Wallet();
		Wallet wallet2 = new Wallet();
		Transaction transaction1 = new Transaction();
		Transaction transaction2 = new Transaction();
		Transaction transaction3 = new Transaction();
		transaction1.setWallet(wallet1);
		transaction2.setWallet(wallet1);
		transaction3.setWallet(wallet2);
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction1);
		transactions.add(transaction2);
		transactions.add(transaction3);
		List<Transaction> expectedTransactions = new ArrayList<Transaction>();
		expectedTransactions.add(transaction1);
		expectedTransactions.add(transaction2);
		when(transactionRepoMock.findByWallet(wallet1)).thenReturn(expectedTransactions);

		//Act
		List<Transaction> actualTransactions = transactionService.findByWallet(wallet1);

		//Assert 
		assertEquals(expectedTransactions, actualTransactions);
		verify(transactionRepoMock,times(1)).findByWallet(wallet1);
	}

}
