package com.cinemaabyss.proxy.core;

import com.cinemaabyss.proxy.config.ProxyProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static org.apache.logging.log4j.util.Strings.EMPTY;

@Component
@RequiredArgsConstructor
public class ProxyServer {

    private static final Set<String> NO_BODY_METHODS = Set.of(HttpMethod.GET.name(), "HEAD");

    private final RestClient restClient = RestClient.create();
    private final ProxyProperties props;

    public ResponseEntity<String> toMonolith(HttpServletRequest req, String body) {
        return forward(req, body, props.monolithUrl());
    }

    public ResponseEntity<String> toEvents(HttpServletRequest req, String body) {
        return forward(req, body, props.eventsUrl());
    }

    public ResponseEntity<String> toMoviesGradually(HttpServletRequest req, String body) {
        return forward(req, body, chooseMoviesBase());
    }

    private ResponseEntity<String> forward(HttpServletRequest req, String body, String base) {
        var target = targetUri(base, req);
        var method = req.getMethod();

        var spec = restClient.method(HttpMethod.valueOf(method))
                .uri(target)
                .headers(h -> copyHeaders(req, h));

        var response = NO_BODY_METHODS.contains(method)
                ? spec.retrieve().toEntity(String.class)
                : spec.body(Optional.ofNullable(body).orElse(EMPTY))
                .retrieve().toEntity(String.class);

        var ct = Optional.ofNullable(response.getHeaders().getContentType())
                .orElse(MediaType.APPLICATION_JSON);

        return ResponseEntity.status(response.getStatusCode())
                .contentType(ct)
                .body(response.getBody());
    }

    private String chooseMoviesBase() {
        if (!props.gradualMigration()) return props.monolithUrl();
        var percent = Math.clamp(props.moviesMigrationPercent(), 0, 100);
        var roll = ThreadLocalRandom.current().nextInt(1, 101);
        return roll <= percent ? props.moviesUrl() : props.monolithUrl();
    }

    private static URI targetUri(String base, HttpServletRequest req) {
        var path = req.getRequestURI();
        var query = req.getQueryString();
        return URI.create(base + path + (query != null && !query.isBlank() ? ("?" + query) : EMPTY));
    }

    private static void copyHeaders(HttpServletRequest req, HttpHeaders out) {
        var names = req.getHeaderNames();
        while (names.hasMoreElements()) {
            var name = names.nextElement();
            if (name.equalsIgnoreCase("host")) continue;
            var values = req.getHeaders(name);
            while (values.hasMoreElements()) {
                out.add(name, values.nextElement());
            }
        }
    }
}
