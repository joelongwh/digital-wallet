package com.fdmgroup.DigitalWalletJoel.Model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author joelongwh
 * Transaction class to generate POJOs
 * Attributes include: id, transactionDate, transactionAmounnt, description and wallet
 * Getters and setters
 * Constructor using fields (excluding id)
 * Constructor from Superclass
 * Overrides toString method
 *
 */
@Entity
public class Transaction {
	
	@Id
	@GeneratedValue
	@Column(name="transaction_id", nullable=false)
	private long id;
	
	@Column(name="date", nullable=false)
	private LocalDate transactionDate;
	
	@Column(name="amount", nullable=false)
	private double transactionAmount;
	
	@Column(name="description", nullable=false)
	private String description;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="fk_wallet_id", nullable=false)
	private Wallet wallet;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public LocalDate getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}
	public double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Wallet getWallet() {
		return wallet;
	}
	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}
	
	public Transaction(LocalDate transactionDate, double transactionAmount, String description, Wallet wallet) {
		super();
		this.transactionDate = transactionDate;
		this.transactionAmount = transactionAmount;
		this.description = description;
		this.wallet = wallet;
	}
	
	public Transaction() {
		super();
	}
	
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", transactionDate=" + transactionDate + ", transactionAmount="
				+ transactionAmount + ", description=" + description + ", wallet=" + wallet + "]";
	}
	
	

}
