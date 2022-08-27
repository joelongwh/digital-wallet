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

import com.fdmgroup.DigitalWalletJoel.Controller.WalletController;
import com.fdmgroup.DigitalWalletJoel.Model.User;
import com.fdmgroup.DigitalWalletJoel.Model.Wallet;
import com.fdmgroup.DigitalWalletJoel.Service.WalletService;

@ExtendWith(MockitoExtension.class)
public class WalletControllerTest {
	
	@Mock
	private WalletService walletServiceMock;
	
	@Mock
	private HttpSession sessionMock;
	
	@Mock
	private Wallet walletMock;
	
	private WalletController walletController;
	
	@BeforeEach
	private void setup() {
		walletController = new WalletController(walletServiceMock);
	}
	
	@Test
	public void test_that_topUpWallet_returnsTopUpFormHtml() {
		//Arrange
		String expected = "topUpForm.html";
		
		//Act
		String actual = walletController.topUpWallet();
		
		//Assert
		assertEquals(expected,actual);
	}
	
	@Test
	public void test_that_processTopUp_setsSessionAttribute_rdirectsToHome() {
		//Arrange
		String expected = "redirect:/home";
		long walletId = 1234;
		double topUpAmount = 100;
		String transactionDescription = "Credit - Top Up";
		when(sessionMock.getAttribute("wallet")).thenReturn(walletMock);
		when(walletMock.getId()).thenReturn(walletId);
		when(walletServiceMock.topUpWallet(walletId, topUpAmount, transactionDescription)).thenReturn(walletMock);
		
		//Act
		String actual = walletController.processTopUp(sessionMock, topUpAmount);
		
		//Assert
		assertEquals(expected,actual);
		verify(sessionMock,times(1)).setAttribute("wallet", walletMock);
	}
	
	@Test
	public void test_that_withdrawFromWallet_returnsWithdrawalFormHtml() {
		//Arrange
		String expected = "withdrawalForm.html";
		
		//Act
		String actual = walletController.withdrawFromWallet();
		
		//Assert
		assertEquals(expected,actual);
	}
	
	@Test
	public void test_that_processWithdrawal_returnsWithdrawalFormHtml_ifInsufficientBalance() {
		//Arrange
		String expected = "withdrawalForm.html";
		double withdrawalAmount = 100;
		long walletId = 1234;
		double currentBalance = 50;
		when(sessionMock.getAttribute("wallet")).thenReturn(walletMock);
		when(walletMock.getId()).thenReturn(walletId);
		when(walletMock.getWalletBalance()).thenReturn(currentBalance);
		
		//Act
		String actual = walletController.processWithdrawal(sessionMock, withdrawalAmount);
		
		//Assert
		assertEquals(expected,actual);
	}
	
	@Test
	public void test_that_processWithdrawal_setsSessionAttribute_redirectsToHome_ifSufficientBalance() {
		//Arrange
		String expected = "redirect:/home";
		double withdrawalAmount = 50;
		long walletId = 1234;
		double currentBalance = 100;
		String transactionDescription = "Debit - Withdrawal";
		when(sessionMock.getAttribute("wallet")).thenReturn(walletMock);
		when(walletMock.getId()).thenReturn(walletId);
		when(walletMock.getWalletBalance()).thenReturn(currentBalance);
		when(walletServiceMock.withdrawFromWallet(walletId, withdrawalAmount, transactionDescription)).thenReturn(walletMock);
		
		//Act
		String actual = walletController.processWithdrawal(sessionMock, withdrawalAmount);
		
		//Assert
		assertEquals(expected,actual);
		verify(sessionMock,times(1)).setAttribute("wallet", walletMock);
	}
	
	@Test
	public void test_that_transfer_returnsTransferFormHtml() {
		//Arrange
		String expected = "transferForm.html";
		
		//Act
		String actual = walletController.transfer();
		
		//Assert
		assertEquals(expected,actual);
	}
	
	@Test
	public void test_that_processTransfer_returnsTransferFormHtml_ifInsufficientBalance() {
		//Arrange
		String expected = "transferForm.html";
		long recipientWalletNumber = 1234;
		Wallet fromWallet = new Wallet();
		double currentBalance = 50;
		Wallet toWallet = new Wallet();
		double transferAmount = 100;
		fromWallet.setWalletBalance(currentBalance);
		when(sessionMock.getAttribute("wallet")).thenReturn(fromWallet);
		when(walletServiceMock.findByWalletNumber(recipientWalletNumber)).thenReturn(toWallet);
		
		//Act
		String actual = walletController.processTransfer(sessionMock, recipientWalletNumber, transferAmount);
		
		//Assert
		assertEquals(expected,actual);
	}
	
	@Test
	public void test_that_processTransfer_returnsTransferFormHtml_ifToWalletIsNull() {
		//Arrange
		String expected = "transferForm.html";
		long recipientWalletNumber = 1234;
		Wallet fromWallet = new Wallet();
		double currentBalance = 50;
		Wallet toWallet = null;
		double transferAmount = 100;
		fromWallet.setWalletBalance(currentBalance);
		when(sessionMock.getAttribute("wallet")).thenReturn(fromWallet);
		when(walletServiceMock.findByWalletNumber(recipientWalletNumber)).thenReturn(toWallet);
		
		//Act
		String actual = walletController.processTransfer(sessionMock, recipientWalletNumber, transferAmount);
		
		//Assert
		assertEquals(expected,actual);
	}
	
	@Test
	public void test_that_processTransfer_returnsTransferFormHtml_ifFromWalletIsToWallet() {
		//Arrange
		String expected = "transferForm.html";
		long recipientWalletNumber = 1234;
		Wallet fromWallet = new Wallet();
		double currentBalance = 50;
		Wallet toWallet = fromWallet;
		double transferAmount = 100;
		fromWallet.setWalletBalance(currentBalance);
		when(sessionMock.getAttribute("wallet")).thenReturn(fromWallet);
		when(walletServiceMock.findByWalletNumber(recipientWalletNumber)).thenReturn(toWallet);
		
		//Act
		String actual = walletController.processTransfer(sessionMock, recipientWalletNumber, transferAmount);
		
		//Assert
		assertEquals(expected,actual);
	}
	
	@Test
	public void test_that_processTransfer_setsSessionAttribute_redirectsToHome_ifValid() {
		//Arrange
		String expected = "redirect:/home";
		long recipientWalletNumber = 1234;
		Wallet toWallet = new Wallet();
		toWallet.setUser(new User());
		double transferAmount = 50;
		String fromTransactionDescription = "Debit - Transferred to null";
		String toTransactionDescription = "Credit - Received from null";
		
		when(sessionMock.getAttribute("wallet")).thenReturn(walletMock);
		when(walletMock.getWalletBalance()).thenReturn((double) 100);
		when(walletServiceMock.findByWalletNumber(recipientWalletNumber)).thenReturn(toWallet);
		when(walletMock.getId()).thenReturn((long) 4321);
		when(walletMock.getUser()).thenReturn(new User());
		when(walletServiceMock.withdrawFromWallet(walletMock.getId(), transferAmount, fromTransactionDescription)).thenReturn(walletMock);
		when(walletServiceMock.topUpWallet(toWallet.getId(), transferAmount, toTransactionDescription)).thenReturn(toWallet);
		
		//Act
		String actual = walletController.processTransfer(sessionMock, recipientWalletNumber, transferAmount);
		
		//Assert
		assertEquals(expected,actual);
		verify(sessionMock,times(1)).setAttribute("wallet", walletMock);
	}

}
