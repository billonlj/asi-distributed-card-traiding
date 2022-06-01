package com.asi.controller;

import com.asi.dto.RoomDto;
import com.asi.model.Room;
import com.asi.service.GameService;
import com.asi.utils.SseHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class GameController {
    private final static Logger LOG = LoggerFactory.getLogger(GameController.class);

    @Autowired
	GameService gameService;

    @PostConstruct
    public void init() {
        gameService.initJob();
    }

    @RequestMapping("/api/rooms")
    @ResponseBody
    public ResponseEntity<?> getrooms() {
        return new ResponseEntity<>(gameService.getAvailableRooms(), HttpStatus.OK);
    }

    @PostMapping("/api/rooms")
    public Room createRoom(@RequestBody RoomDto roomDto) {
        return gameService.createRoom(roomDto);
    }


    @RequestMapping("/api/rooms/sse")
    public SseEmitter streamRooms() {
        return gameService.getEmitterAvailaleRooms();
    }

    @GetMapping(value = "/api/rooms/sse/join/{idRoom}")
    public SseEmitter join(@PathVariable int idRoom) {
        return gameService.joinRoom(idRoom);
    }
}
