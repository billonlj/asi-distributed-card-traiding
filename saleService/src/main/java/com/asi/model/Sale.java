package com.asi.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Sale {
	@Id
	@GeneratedValue
	@Column
	private int idSale;
	@Column
	private int userIdSale;
	@Column
	private int cardInstanceIdSale;
	@Column
	private double priceSale;
	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	private Date dateSale;

	
	public Sale(int userIdSale, int cardInstanceIdSale, double priceSale) {
		this.userIdSale = userIdSale;
		this.cardInstanceIdSale = cardInstanceIdSale;
		this.priceSale = priceSale;
	}

	public Sale() {
	}

	public int getIdSale() {
		return this.idSale;
	}

	public void setIdSale(int idSale) {
		this.idSale = idSale;
	}

	public int getUserIdSale() {
		return this.userIdSale;
	}

	public void setUserIdSale(int userIdSale) {
		this.userIdSale = userIdSale;
	}

	public int getCardInstanceIdSale() {
		return this.cardInstanceIdSale;
	}

	public void setCardInstanceIdSale(int cardInstanceIdSale) {
		this.cardInstanceIdSale = cardInstanceIdSale;
	}

	public double getPriceSale() {
		return this.priceSale;
	}

	public void setPriceSale(double priceSale) {
		this.priceSale = priceSale;
	}

	public Date getDateSale() {
		return this.dateSale;
	}

	public void setDateSale(Date dateSale) {
		this.dateSale = dateSale;
	}

	
}
