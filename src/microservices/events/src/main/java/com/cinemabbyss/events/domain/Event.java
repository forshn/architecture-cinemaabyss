package com.cinemabbyss.events.domain;

public record Event(String id, String type, String timestamp, Object payload) {}
