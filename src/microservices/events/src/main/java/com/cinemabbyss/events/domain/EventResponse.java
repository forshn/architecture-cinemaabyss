package com.cinemabbyss.events.domain;

public record EventResponse(String status, int partition, long offset, Event event) {}
