package com.asi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Family {
	@Id
	@GeneratedValue
	@Column
	private int idFamily;
	@Column
	private String nameFamily;
	
	public Family() {}
	
	public Family(int idFamily, String nameFamily) {
		super();
		this.idFamily = idFamily;
		this.nameFamily = nameFamily;
	}

	public int getIdFamily() {
		return idFamily;
	}

	public void setIdFamily(int idFamily) {
		this.idFamily = idFamily;
	}

	public String getNameFamily() {
		return nameFamily;
	}

	public void setNameFamily(String nameFamily) {
		this.nameFamily = nameFamily;
	}
	
}
