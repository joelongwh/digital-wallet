package com.fdmgroup.DigitalWalletJoel.Model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author joelongwh
 * Wallet class to generate POJOs
 * Attributes include: id, walletNumber, walletBalance, user
 * Getters and setters
 * Constructor using fields (excluding id)
 * Constructor from Superclass
 * Overrides toString method
 */
@Entity
public class Wallet {
	
	@Id
	@GeneratedValue
	@Column(name="wallet_id", nullable=false)
	private long id;
	
	@Column(name="wallet_number", unique=true, nullable=false)
	private long walletNumber;
	
	@Column(name="wallet_balance", nullable=false)
	private double walletBalance;
	
	@OneToOne(mappedBy="wallet")
	@JsonBackReference
	private User user;
	
	@OneToMany(mappedBy="wallet", cascade = CascadeType.ALL)
	private List<Transaction> transactions = new ArrayList<Transaction>();
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getWalletNumber() {
		return walletNumber;
	}
	public void setWalletNumber(long walletNumber) {
		this.walletNumber = walletNumber;
	}
	public double getWalletBalance() {
		return walletBalance;
	}
	public void setWalletBalance(double walletBalance) {
		this.walletBalance = walletBalance;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}
	
	public Wallet() {
		super();
		this.walletNumber = 100000L + (long) (Math.random() * (999999L - 100000L));
	}
	
	@Override
	public String toString() {
		return "Wallet [id=" + id + ", walletNumber=" + walletNumber + ", walletBalance=" + walletBalance + "]";
	}
	
	

}
