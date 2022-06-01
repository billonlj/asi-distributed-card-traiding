package com.asi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Card {
	@Id
	@GeneratedValue
	private int idCard;
	@Column
	private String nameCard;
	@Column
	private String descriptionCard;
	@Column
	private String affinityCard;
	@Column
	private int energyCard;
	@Column
	private int hpCard;
	@Column
	private int attackCard;
	@Column
	private int defenceCard;
	@Column
	private String sourceUrlCard;
	@ManyToOne
	private Family familyCard;
	
	public Card() {}
	
	public Card(int idCard, String nameCard, String descriptionCard, String affinityCard, int energyCard, String sourceUrlCard) {
		super();
		this.idCard = idCard;
		this.nameCard = nameCard;
		this.descriptionCard = descriptionCard;
		this.affinityCard = affinityCard;
		this.sourceUrlCard = sourceUrlCard;
		this.energyCard = energyCard;
	}

	public Card(int idCard, String affinityCard, String descriptionCard, int energyCard, String nameCard, Family familyCard, int attackCard, int hpCard, int defenceCard, String sourceUrlCard) {
		super();
		this.idCard = idCard;
		this.affinityCard = affinityCard;
		this.descriptionCard = descriptionCard;
		this.energyCard = energyCard;
		this.nameCard = nameCard;
		this.familyCard = familyCard;
		this.attackCard = attackCard;
		this.hpCard = hpCard;
		this.defenceCard = defenceCard;
		this.sourceUrlCard = sourceUrlCard;
	}

	public int getIdCard() {
		return idCard;
	}

	public void setIdCard(int idCard) {
		this.idCard = idCard;
	}

	public String getNameCard() {
		return nameCard;
	}

	public void setNameCard(String nameCard) {
		this.nameCard = nameCard;
	}

	public String getDescriptionCard() {
		return descriptionCard;
	}

	public void setDescriptionCard(String descriptionCard) {
		this.descriptionCard = descriptionCard;
	}

	public String getAffinityCard() {
		return affinityCard;
	}

	public void setAffinityCard(String affinityCard) {
		this.affinityCard = affinityCard;
	}

	public int getEnergyCard() {
		return energyCard;
	}

	public void setEnergyCard(int energyCard) {
		this.energyCard = energyCard;
	}

	public String getSourceUrlCard() {
		return sourceUrlCard;
	}

	public void setSourceUrlCard(String sourceUrlCard) {
		this.sourceUrlCard = sourceUrlCard;
	}

	public Family getFamilyCard() {
		return familyCard;
	}

	public void setFamilyCard(Family familyCard) {
		this.familyCard = familyCard;
	}

	public int getHpCard() {
		return hpCard;
	}

	public void setHpCard(int hpCard) {
		this.hpCard = hpCard;
	}

	public int getAttackCard() {
		return attackCard;
	}

	public void setAttackCard(int attackCard) {
		this.attackCard = attackCard;
	}

	public int getDefenceCard() {
		return defenceCard;
	}

	public void setDefenceCard(int defenceCard) {
		this.defenceCard = defenceCard;
	}
}
