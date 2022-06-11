package com.asi.model;

import com.asi.dto.CardInstanceDto;

public class Player {
    private int idUser;
    private int idCardInstance;

	private CardInstanceDto cardInstance;

	public Player(int idUser, int idCardInstance, CardInstanceDto cardInstance) {
		this.idUser = idUser;
		this.idCardInstance = idCardInstance;
		this.cardInstance = cardInstance;
	}

    public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

    public int getIdCardInstance() {
		return idCardInstance;
	}
	public void setIdCardInstance(int idCardInstance) {
		this.idCardInstance = idCardInstance;
	}

	public CardInstanceDto getCardInstance() {
		return cardInstance;
	}
	public void setIdUser(CardInstanceDto cardInstance) {
		this.cardInstance = cardInstance;
	}
}
