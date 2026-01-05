package com.cinemabbyss.events.web;

import com.cinemabbyss.events.domain.EventResponse;
import com.cinemabbyss.events.domain.MovieEvent;
import com.cinemabbyss.events.domain.PaymentEvent;
import com.cinemabbyss.events.domain.UserEvent;
import com.cinemabbyss.events.domain.PublishResult;
import com.cinemabbyss.events.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/api/events", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EventsController {

    private final EventService events;

    @GetMapping("/health")
    public Map<String, Boolean> health() { return Map.of("status", true); }

    @PostMapping(path = "/movie", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventResponse> movie(@RequestBody MovieEvent event) {
        var res = events.publishMovie(event);
        return toResponse(res);
    }

    @PostMapping(path = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventResponse> user(@RequestBody UserEvent event) {
        var res = events.publishUser(event);
        return toResponse(res);
    }

    @PostMapping(path = "/payment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventResponse> payment(@RequestBody PaymentEvent event) {
        var res = events.publishPayment(event);
        return toResponse(res);
    }

    private static ResponseEntity<EventResponse> toResponse(PublishResult res) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new EventResponse("success", res.partition(), res.offset(), res.event()));
    }
}
