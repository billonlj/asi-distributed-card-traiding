package com.asi.model;


import com.asi.enums.GameStatus;
import com.asi.utils.SseHandler;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class Room {
    private Integer idRoom;
    private String nameRoom;
    private GameStatus status = GameStatus.AVAILABLE;

    public SseHandler emitterRoom = new SseHandler();
    public int numberOfPlayers = 0;

    public Room(Integer idRoom, String nameRoom) {
        this.idRoom = idRoom;
        this.nameRoom = nameRoom;
    }

    public Room (Integer idRoom, String nameRoom, SseHandler emitterRoom) {
        this.idRoom = idRoom;
        this.nameRoom = nameRoom;
        this.emitterRoom = emitterRoom;
    }

    public SseEmitter addPlayer() {
        // block new connections if the game is already full or has started
        if(this.getStatus() == GameStatus.FULL || this.getStatus() == GameStatus.STARTED) {
            return null;
        } 

        SseEmitter newPlayer = this.emitterRoom.addClient();
        this.numberOfPlayers = this.emitterRoom.emitters.size();

        if(this.numberOfPlayers >= 2){
            this.setStatus(GameStatus.FULL);
        }

        return newPlayer;
    }

    public GameStatus getStatus() {
        return this.status;
    }
    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public Integer getIdRoom() {
        return this.idRoom;
    }
    public void setIdRoom(Integer idRoom) {
        this.idRoom = idRoom;
    }

    public String getNameRoom() {
        return this.nameRoom;
    }
    public void setNameRoom(String nameRoom) {
        this.nameRoom = nameRoom;
    }

    public SseHandler getHandlerRoom() {
        return this.emitterRoom;
    }
    public void setHandlerRoom(SseHandler emitterRoom) {
        this.emitterRoom = emitterRoom;
    }
}
