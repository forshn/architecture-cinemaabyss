package com.cinemaabyss.proxy.web;

import com.cinemaabyss.proxy.core.ProxyServer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProxyController {

    private final ProxyServer proxy;

    @GetMapping("/health")
    public Map<String, Boolean> health() { return Map.of("status", true); }

    @RequestMapping(path = "/api/users/**")
    public ResponseEntity<String> users(HttpServletRequest request, @RequestBody(required = false) String body) {
        return proxy.toMonolith(request, body);
    }

    @RequestMapping(path = "/api/events/**")
    public ResponseEntity<String> events(HttpServletRequest request, @RequestBody(required = false) String body) {
        return proxy.toEvents(request, body);
    }

    @RequestMapping(path = "/api/movies/**")
    public ResponseEntity<String> movies(HttpServletRequest request, @RequestBody(required = false) String body) {
        return proxy.toMoviesGradually(request, body);
    }

    @RequestMapping(path = "/api/**")
    public ResponseEntity<String> fallback(HttpServletRequest request, @RequestBody(required = false) String body) {
        return proxy.toMonolith(request, body);
    }
}
