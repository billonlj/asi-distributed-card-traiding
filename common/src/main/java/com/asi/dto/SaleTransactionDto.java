package com.asi.dto;

public class SaleTransactionDto {
	
	private double priceSale;
	private int idSale;
	//TODO rename en cardInstanceId
	private int idCard;
	//TODO delete ou rename en userId
	private int idUser;
	
	public double getPriceSale() {
		return priceSale;
	}
	public void setPriceSale(double price) {
		this.priceSale = price;
	}
	public int getIdSale() {
		return idSale;
	}
	public void setIdSale(int idSale) {
		this.idSale = idSale;
	}
	public int getIdCard() {
		return idCard;
	}
	public void setIdCard(int idCard) {
		this.idCard = idCard;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
		
}
