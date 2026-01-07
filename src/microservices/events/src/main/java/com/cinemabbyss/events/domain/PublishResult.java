package com.cinemabbyss.events.domain;

public record PublishResult(int partition, long offset, Event event) {}
