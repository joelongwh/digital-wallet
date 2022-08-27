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
import org.springframework.ui.Model;

import com.fdmgroup.DigitalWalletJoel.Controller.TransactionController;
import com.fdmgroup.DigitalWalletJoel.Model.Wallet;
import com.fdmgroup.DigitalWalletJoel.Service.TransactionService;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {
	
	@Mock
	private TransactionService transactionServiceMock;
	
	@Mock
	private Model modelMock;
	
	@Mock
	private HttpSession sessionMock;
	
	@Mock
	private Wallet walletMock;
	
	private TransactionController transactionController;
	
	@BeforeEach
	private void setup() {
		transactionController = new TransactionController(transactionServiceMock);
	}
	
	@Test
	public void test_that_viewTransactionHistory_addsModelAttribute_returnsViewTransactionsHtml() {
		//Arrange
		String expected = "viewTransactions.html";
		when(sessionMock.getAttribute("wallet")).thenReturn(walletMock);
		
		//Act
		String actual = transactionController.viewTransactionHistory(sessionMock, modelMock);
		
		//Assert
		assertEquals(expected,actual);
		verify(modelMock,times(1)).addAttribute("transactions", this.transactionServiceMock.findByWallet(walletMock));
	}

}
