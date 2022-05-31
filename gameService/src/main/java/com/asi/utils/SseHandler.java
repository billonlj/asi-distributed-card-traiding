package com.asi.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class SseHandler {
    public final Map<String, SseEmitter> emitters = new HashMap<String, SseEmitter>();
    private final static Logger LOG = LoggerFactory.getLogger(SseHandler.class);
    
    public SseHandler() {
    }

    public SseEmitter addClient() {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        String client = LocalDateTime.now().toString();

        LOG.info("Emitter creation: " + client);

        sseEmitter.onCompletion(() -> {
            LOG.info("Emitter destruction: " + client);
            emitters.remove(client);
        });
        // sseEmitter.onTimeout(() -> System.out.println("SseEmitter is timed out"));
        // sseEmitter.onError((ex) -> System.out.println("SseEmitter got error:" + ex));

        emitters.put(client, sseEmitter);
        return sseEmitter;
    }
}
