package com.project.System.service.events;

import com.project.System.entity.EventsEntity;
import com.project.System.model.Events;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface EventsService {
    ResponseEntity<?>
    registerEvent(Events event, MultipartFile file);

    ResponseEntity<?> viewEvents();

    ResponseEntity<?> returnPhoto(String fileName);

    EventsEntity findById(int eventId);
}
