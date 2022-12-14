package com.fdmgroup.DigitalWalletJoel.Model;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * @author joelongwh
 * User class to generate POJOs
 * Attributes include: id, username, password, wallet
 * Getters and setters
 * Constructors using fields (excluding id)
 * Constructor from Superclass
 * Overrides toString method
 * Overrides hashCode and equals methods
 */
@Entity
public class User {
	
	@Id
	@GeneratedValue
	@Column(name="user_id", nullable=false)
	private long id;
	
	@Column(name="username", nullable=false)
	private String username;
	
	@Column(name="password", nullable=false)
	private String password;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JsonManagedReference
	@JoinColumn(name="fk_wallet_id", nullable=false)
	private Wallet wallet;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Wallet getWallet() {
		return wallet;
	}
	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}
	
	public User(String username, String password, Wallet wallet) {
		super();
		this.username = username;
		this.password = password;
		this.wallet = wallet;
	}
	
	public User() {
		super();
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", wallet=" + wallet + "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, password, username, wallet);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return id == other.id && Objects.equals(password, other.password) && Objects.equals(username, other.username);
//				&& Objects.equals(wallet, other.wallet);
	}
	
	

}
