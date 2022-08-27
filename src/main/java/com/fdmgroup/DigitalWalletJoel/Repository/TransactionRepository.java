package com.fdmgroup.DigitalWalletJoel.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.DigitalWalletJoel.Model.Transaction;
import com.fdmgroup.DigitalWalletJoel.Model.Wallet;

/**
 * @author joelongwh
 * TransactionRepository interface that extends JpaRepository to access JPA methods
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	List<Transaction> findByWallet(Wallet wallet);

}
