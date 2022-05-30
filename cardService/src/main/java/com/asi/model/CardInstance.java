package com.asi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CardInstance {
	@Id
	@GeneratedValue
	@Column
	private int idInstance;
	@ManyToOne
	private Card cardInstance;
	@Column
	private int idUser;
	@Column
	private int energyInstance;
	@Column
	private int hpInstance;
	@Column
	private int attackInstance;
	@Column
	private int defenceInstance;
	
	public CardInstance() {}
	
	public CardInstance(Card cardInstance, int idUser, int energyInstance, int hpInstance,
			int attackInstance, int defenceInstance) {
		super();
		this.cardInstance = cardInstance;
		this.idUser = idUser;
		this.energyInstance = energyInstance;
		this.hpInstance = hpInstance;
		this.attackInstance = attackInstance;
		this.defenceInstance = defenceInstance;
	}

	public int getIdInstance() {
		return idInstance;
	}
	public void setIdInstance(int idInstance) {
		this.idInstance = idInstance;
	}
	public Card getCardInstance() {
		return cardInstance;
	}
	public void setCardInstance(Card cardInstance) {
		this.cardInstance = cardInstance;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
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
	public int getAttackInstance() {
		return attackInstance;
	}
	public void setAttackInstance(int attackInstance) {
		this.attackInstance = attackInstance;
	}
	public int getDefenceInstance() {
		return defenceInstance;
	}
	public void setDefenceInstance(int defenceInstance) {
		this.defenceInstance = defenceInstance;
	}
	
	
	
	
	
}
