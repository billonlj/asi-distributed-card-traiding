package com.asi.dto;

public class CardInstanceDto {
	private int idInstance;
	private int cardIdInstance;
	private CardDto card;
	private int iduser;
	private int energyInstance;
	private int hpInstance;
	private int attackinstance;
	private int defenceInstance;
	
	public CardInstanceDto (int idInstance,int cardInstance,int iduser,int energyInstance,int hpInstance,int attackinstance,int defenceInstance) {
		this.idInstance = idInstance;
		this.cardInstance = cardInstance;
		this.iduser = iduser;
		this.energyInstance = energyInstance;
		this.hpInstance = hpInstance;
		this.attackinstance = attackinstance;
		this.defenceInstance = defenceInstance;
	}

	public int getIdInstance() {
		return idInstance;
	}
	public void setIdInstance(int idInstance) {
		this.idInstance = idInstance;
	}
	public int getCardIdInstance() {
		return cardIdInstance;
	}
	public void setCardIdInstance(int cardInstance) {
		this.cardIdInstance = cardInstance;
	}
	public int getIduser() {
		return iduser;
	}
	public void setIduser(int iduser) {
		this.iduser = iduser;
	}
	public int getEnergyInstance() {
		return energyInstance;
	}
	public void setEnergyInstance(int energyInstance) {
		this.energyInstance = energyInstance;
	}
	public int getHpInstance() {
		return hpInstance;
	}
	public void setHpInstance(int hpInstance) {
		this.hpInstance = hpInstance;
	}
	public int getAttackinstance() {
		return attackinstance;
	}
	public void setAttackinstance(int attackinstance) {
		this.attackinstance = attackinstance;
	}
	public int getDefenceInstance() {
		return defenceInstance;
	}
	public void setDefenceInstance(int defenceInstance) {
		this.defenceInstance = defenceInstance;
	}
	public CardDto getCard() {
		return card;
	}
	public void setCard(CardDto cardInstance) {
		this.card = cardInstance;
	}
}