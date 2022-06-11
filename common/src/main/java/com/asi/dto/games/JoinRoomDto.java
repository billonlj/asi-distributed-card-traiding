package com.asi.dto.games;

public class JoinRoomDto {
    private int idUser;
    private int idCardInstance;
    private int idRoom;

    public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

    public int getIdRoom() {
		return idRoom;
	}
	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}

    public int getIdCardInstance() {
		return idCardInstance;
	}
	public void setIdCardInstance(int idCardInstance) {
		this.idCardInstance = idCardInstance;
	}
}
