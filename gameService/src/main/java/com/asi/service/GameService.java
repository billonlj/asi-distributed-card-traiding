package com.asi.service;

import com.asi.model.Room;
import com.asi.utils.SseHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class GameService {
    Map<Integer, Room> rooms = new HashMap<Integer, Room>();
    SseHandler roomsHandler = new SseHandler();

    public void initJob() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            while(true) {
                for (Room room : rooms.values()) {
                    SseHandler handler = room.emitterRoom;
    
                    for (Map.Entry<String, SseEmitter> connection : handler.emitters.entrySet()) {
                        SseEmitter emitter = connection.getValue();
                        try {
                            emitter.send(room);
                        } catch (IOException e) {
                        }
                    }
                }
                randomDelay();
            }
        });
    }

    private void randomDelay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public Collection<Room> getAvailableRooms() {
        return rooms.values();
    }

    public Room createRoom() {
        Integer size = rooms.size();
        Room newRoom = new Room(size);
        System.out.println("Create room: " + size);
        rooms.put(size, newRoom);
        dispatchrooms();
        return newRoom;
    }

    public SseEmitter joinRoom(int idRoom) {
        if(!rooms.containsKey(idRoom)) 
            return null;

        return rooms.get(idRoom).addPlayer();
    }

    public SseEmitter getEmitterAvailaleRooms() {
        return roomsHandler.addClient();
    }


    private void dispatchrooms() {
        Collection<Room> message = rooms.values();
        for (Map.Entry<String, SseEmitter> connection : roomsHandler.emitters.entrySet()) {
            try {
                SseEmitter emitter = connection.getValue();
                emitter.send(message);
            } catch (IOException e) {

            }
        }
    }
}
