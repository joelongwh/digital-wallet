package com.fdmgroup.DigitalWalletJoel.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.DigitalWalletJoel.Model.Wallet;

/**
 * @author joelongwh
 * WalletRepository interface that extends JpaRepository to access JPA methods
 */
@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

	Wallet findByWalletNumber(long recipientWalletNumber);
	
}
