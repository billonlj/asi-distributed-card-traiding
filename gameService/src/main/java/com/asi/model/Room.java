package com.asi.model;


import com.asi.utils.SseHandler;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class Room {
    private Integer idRoom;
    public SseHandler emitterRoom = new SseHandler();
    public int numberOfPlayers = 0;

    public Room(Integer idRoom) {
        this.idRoom = idRoom;
    }

    public Room (Integer idRoom, SseHandler emitterRoom) {
        this.idRoom = idRoom;
        this.emitterRoom = emitterRoom;
    }

    public SseEmitter addPlayer() {
        SseEmitter newPlayer = this.emitterRoom.addClient();
        this.numberOfPlayers = this.emitterRoom.emitters.size();
        return newPlayer;
    }

    public Integer getIdRoom() {
        return this.idRoom;
    }
    public void setIdRoom(Integer idRoom) {
        this.idRoom = idRoom;
    }

    public SseHandler getHandlerRoom() {
        return this.emitterRoom;
    }
    public void setHandlerRoom(SseHandler emitterRoom) {
        this.emitterRoom = emitterRoom;
    }
}
