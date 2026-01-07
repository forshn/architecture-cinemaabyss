package com.cinemabbyss.events.service;

import com.cinemabbyss.events.domain.MovieEvent;
import com.cinemabbyss.events.domain.PaymentEvent;
import com.cinemabbyss.events.domain.PublishResult;
import com.cinemabbyss.events.domain.UserEvent;

public interface EventService {
    PublishResult publishMovie(MovieEvent event);
    PublishResult publishUser(UserEvent event);
    PublishResult publishPayment(PaymentEvent event);
}
