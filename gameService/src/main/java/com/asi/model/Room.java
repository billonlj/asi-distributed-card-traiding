package com.asi.model;


import com.asi.enums.GameStatus;
import com.asi.utils.SseHandler;

import java.io.IOException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class Room {
    private Integer idRoom;
    private String nameRoom;
    private GameStatus status = GameStatus.AVAILABLE;

    private SseHandler emitterRoom = new SseHandler();
    public int numberOfPlayers = 0;


    Map<Integer, Player> players = new HashMap<Integer, Player>();
    private final Map<Integer, Player> waitingPlayers = new HashMap<Integer, Player>();

    public Room(Integer idRoom, String nameRoom) {
        this.idRoom = idRoom;
        this.nameRoom = nameRoom;
    }

    public Room (Integer idRoom, String nameRoom, SseHandler emitterRoom) {
        this.idRoom = idRoom;
        this.nameRoom = nameRoom;
        this.emitterRoom = emitterRoom;
    }

    public void addPlayer(Player player) {
        if(players.containsKey(player.getIdUser())) 
            return;

        waitingPlayers.put(Integer.valueOf(player.getIdUser()), player);
    }

    public SseEmitter joinRoom(int idPlayer) {
        Integer idCurrentPlayer = Integer.valueOf(idPlayer);


        if(players.containsKey(idCurrentPlayer)) {
            return this.emitterRoom.addClient();
        }

        if(!waitingPlayers.containsKey(idCurrentPlayer)) {
            return null;
        }

        // block new connections if the game is already full or has started
        if(status == GameStatus.FULL || status == GameStatus.STARTED) {
            return null;
        }

        players.put(idCurrentPlayer, waitingPlayers.get(idCurrentPlayer));
        waitingPlayers.remove(idCurrentPlayer);

        SseEmitter newPlayer = this.emitterRoom.addClient();
        this.numberOfPlayers = players.size();

        if(this.numberOfPlayers >= 2){
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                this.startGame();
            });
        } else {
            dispatchUpdate();
        }

        return newPlayer;
    }

    private void startGame() {
        try {
            this.setStatus(GameStatus.FULL);
            dispatchUpdate();
            Thread.sleep(3000);
            this.setStatus(GameStatus.STARTED);
            dispatchUpdate();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
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

    public Map<Integer, Player> getPlayers() {
        return this.players;
    }
    public void setPlayers(Map<Integer, Player> players) {
        this.players = players;
    }



    private void dispatchUpdate() {
        for (Map.Entry<String, SseEmitter> connection : emitterRoom.emitters.entrySet()) {
            try {
                SseEmitter emitter = connection.getValue();
                emitter.send(this);
            } catch (IOException e) {
                
            }
        }
    }
}
