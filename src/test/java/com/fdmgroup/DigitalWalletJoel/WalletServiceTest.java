package com.fdmgroup.DigitalWalletJoel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.DigitalWalletJoel.Model.Wallet;
import com.fdmgroup.DigitalWalletJoel.Repository.WalletRepository;
import com.fdmgroup.DigitalWalletJoel.Service.WalletService;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {
	
	@Mock
	private WalletRepository walletRepoMock;
		
	private WalletService walletService;
	
	@BeforeEach
	public void setup() {
		walletService = new WalletService(walletRepoMock);
	}
	
	@Test
	public void test_that_findById_returnsNull_whenInvalid() {
		//Arrange
		Long id = 1l;
		when(walletRepoMock.findById(id)).thenReturn(Optional.ofNullable(null));
				
		//Act
		Wallet actual = walletService.findById(id);
				
		//Assert
		assertNull(actual);
		verify(walletRepoMock,times(1)).findById(id);
	}
	
	@Test
	public void test_that_findById_returnsUser_whenValid() {
		//Arrange
		Wallet expected = new Wallet();
		Long id = 1l;
		when(walletRepoMock.findById(id)).thenReturn(Optional.of(expected));
		
		//Act
		Wallet actual = walletService.findById(id);
		
		//Assert
		assertEquals(actual,expected);
		verify(walletRepoMock,times(1)).findById(id);
	}
	
	@Test
	public void test_that_findByWalletNumber_calls_userRepoMock() {
		//Arrange
		
		//Act
		walletService.findByWalletNumber(123456);
		
		//Assert
		verify(walletRepoMock,times(1)).findByWalletNumber(123456);
	}
	
	@Test
	public void test_that_topUpWallet_setsWalletBalance_addsTransaction_andSavesWallet() {
		//Arrange
		long walletId = 1l;
		double topUpAmount = 100;
		String transactionDescription = "description";
		Wallet expectedWallet = new Wallet();
		when(walletRepoMock.findById(walletId)).thenReturn(Optional.of(expectedWallet));
		
		//Act
		Wallet actualWallet = walletService.topUpWallet(walletId, topUpAmount, transactionDescription);
		
		//Assert
		assertEquals(expectedWallet,actualWallet);
		verify(walletRepoMock,times(1)).save(expectedWallet);
	}
	
	@Test
	public void test_that_withdrawFromWallet_setsWalletBalance_addsTransaction_andSavesWallet() {
		//Arrange
		long walletId = 1l;
		double topUpAmount = 100;
		String transactionDescription = "description";
		Wallet expectedWallet = new Wallet();
		when(walletRepoMock.findById(walletId)).thenReturn(Optional.of(expectedWallet));
		
		//Act
		Wallet actualWallet = walletService.withdrawFromWallet(walletId, topUpAmount, transactionDescription);
		
		//Assert
		assertEquals(expectedWallet,actualWallet);
		verify(walletRepoMock,times(1)).save(expectedWallet);
	}

}
