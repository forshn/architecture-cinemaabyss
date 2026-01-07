package com.cinemabbyss.events.domain;

public record UserEvent(
        Integer user_id,
        String username,
        String email,
        String action,
        String timestamp
) {}
