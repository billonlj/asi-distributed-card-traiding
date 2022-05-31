package com.asi.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue
	@Column
	private int idUser;
	@Column
	private String nameUser;
	@Column
	private String surnameUser;
	@Column(unique=true)
	private String emailUser;
	@Column
	private double moneyUser;
	@Column
	private String passwordUser;
	
	public User() {
	}
	
	public User(int idUser, String nameUser, String surnameUser, String emailUser, double moneyUser, String passwordUser) {
		super();
		this.idUser = idUser;
		this.nameUser = nameUser;
		this.surnameUser = surnameUser;
		this.emailUser = emailUser;
		this.moneyUser = moneyUser;
		this.passwordUser = passwordUser;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getNameUser() {
		return nameUser;
	}

	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}

	public String getSurnameUser() {
		return surnameUser;
	}

	public void setSurnameUser(String surnameUser) {
		this.surnameUser = surnameUser;
	}
	
	public String getEmailUser() {
		return emailUser;
	}

	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}

	public double getMoneyUser() {
		return moneyUser;
	}

	public void setMoneyUser(double moneyUser) {
		this.moneyUser = moneyUser;
	}

	public String getPasswordUser() {
		return passwordUser;
	}

	public void setPasswordUser(String passwordUser) {
		this.passwordUser = passwordUser;
	}
	
	public void hashPassword() {
		this.passwordUser = BCrypt.hashpw(this.passwordUser, BCrypt.gensalt());
	}
	
}
