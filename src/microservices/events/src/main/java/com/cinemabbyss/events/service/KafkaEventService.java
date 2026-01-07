package com.cinemabbyss.events.service;

import com.cinemabbyss.events.domain.Event;
import com.cinemabbyss.events.domain.MovieEvent;
import com.cinemabbyss.events.domain.PaymentEvent;
import com.cinemabbyss.events.domain.PublishResult;
import com.cinemabbyss.events.domain.UserEvent;
import com.cinemabbyss.events.kafka.EventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class KafkaEventService implements EventService {

    private final EventProducer producer;

    @Override
    public PublishResult publishMovie(MovieEvent event) {
        var env = new Event("movie-" + event.movie_id() + "-" + event.action(), "movie", Instant.now().toString(), event);
        var res = producer.send("movie-events", env);
        return new PublishResult(res.getRecordMetadata().partition(), res.getRecordMetadata().offset(), env);
    }

    @Override
    public PublishResult publishUser(UserEvent event) {
        var ts = event.timestamp() != null ? event.timestamp() : Instant.now().toString();
        var env = new Event("user-" + event.user_id() + "-" + event.action(), "user", ts, event);
        var res = producer.send("user-events", env);
        return new PublishResult(res.getRecordMetadata().partition(), res.getRecordMetadata().offset(), env);
    }

    @Override
    public PublishResult publishPayment(PaymentEvent event) {
        var ts = event.timestamp() != null ? event.timestamp() : Instant.now().toString();
        var env = new Event("payment-" + event.payment_id() + "-" + event.status(), "payment", ts, event);
        var res = producer.send("payment-events", env);
        return new PublishResult(res.getRecordMetadata().partition(), res.getRecordMetadata().offset(), env);
    }
}
