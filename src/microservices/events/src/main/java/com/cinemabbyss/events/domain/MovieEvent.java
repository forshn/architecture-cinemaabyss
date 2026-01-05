package com.cinemabbyss.events.domain;

import java.util.List;

public record MovieEvent(
        Integer movie_id,
        String title,
        String action,
        Integer user_id,
        Double rating,
        List<String> genres,
        String description
) {}
