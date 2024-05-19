package com.project.System.service.events;

import com.project.System.entity.EventsEntity;
import com.project.System.exception.CustomMessage;
import com.project.System.model.Events;
import com.project.System.repository.EventsRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.util.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class EventsServiceImpl implements EventsService {

    @Autowired
    private EventsRepository eventsRepository;

    @Value("${project.image}")
    private String path;

    @Override
    public ResponseEntity<?> registerEvent(Events event, MultipartFile file) {

        try {
            String originalFileName = file.getOriginalFilename();
            String extension = "";
            int lastDotIndex = originalFileName.lastIndexOf('.');
            if (lastDotIndex != -1) {
                extension = originalFileName.substring(lastDotIndex);
            }
            String fileName = UUID.randomUUID() +  extension;
            String filePath = path + File.separator + fileName;
            File f = new File(path);
            if (!f.exists()) {
                f.mkdir();
            }
            Files.copy(file.getInputStream(), Paths.get(filePath));

            EventsEntity eventsEntity = EventsEntity.builder().
                    eventName(event.getEventName())
                    .eventDescription(event.getEventDescription())
                    .eventPhoto("/users/events/photo?fileName=" + fileName)
                    .startDate(new Timestamp(event.getStartDate()))
                    .endDate(new Timestamp(event.getEndDate()))
                    .build();
            eventsRepository.save(eventsEntity);
            return new ResponseEntity<>(new CustomMessage("Event Registered Successfully"), HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> viewEvents() {
        List<EventsEntity> eventsEntityList = eventsRepository.findAll();
        List<Events> eventsList = eventsEntityList.stream()
                .map(eventData ->{
                    Events events = new Events();
                    events.setId(eventData.getId());
                    events.setEventName(eventData.getEventName());
                    events.setEventDescription(eventData.getEventDescription());
                    events.setEventPhoto("http://localhost:8080/api"+eventData.getEventPhoto());
                    events.setStartDate(eventData.getStartDate().getTime());
                    events.setEndDate(eventData.getEndDate().getTime());
                    return events;
                }).collect(Collectors.toList());
        return new ResponseEntity<>(eventsList,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> returnPhoto(String fileName) {
        String filePath = path + File.separator+fileName;
        Logger.getAnonymousLogger().log(Level.FINE,filePath);
        try{
            InputStream inputStream = new FileInputStream(filePath);
            byte[] out = IOUtils.toByteArray(inputStream);
            HttpHeaders responseHeaders=new HttpHeaders();
            responseHeaders.set("charset","utf-8");
            responseHeaders.setContentType(MediaType.IMAGE_JPEG);
            return  new ResponseEntity<>(out,responseHeaders,HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(CustomMessage.builder().message(e.getMessage()));
        }
    }

    @Override
    public EventsEntity findById(int eventId) {
        return eventsRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event Not found"));
    }
}
