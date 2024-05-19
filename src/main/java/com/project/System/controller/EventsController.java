package com.project.System.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.System.model.Events;
import com.project.System.service.events.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class EventsController {

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${project.image}")
    private String path;
    @Autowired
    private EventsService eventsService;

    @PostMapping("/admin/events/register")
    public ResponseEntity<?> registerEvent(@RequestParam("event") String data,
                                           @RequestParam("file") MultipartFile file) throws IOException {
        Events event = objectMapper.readValue(data,Events.class);
        //validate
        return eventsService.registerEvent(event,file);
    }

    @GetMapping("/users/events/view")
    public ResponseEntity<?> viewEvents(){
        return eventsService.viewEvents();
    }

    @GetMapping("/users/events/photo")
    public ResponseEntity<?> displayPhoto(@RequestParam("fileName") String fileName){
        return eventsService.returnPhoto(fileName);
    }
}
