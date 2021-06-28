package ru.balacetracker.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MessageCode {

    UNKNOWN,
    REST_EXCHANGE_FAILURE("btg.rest-exchange-failure"),
    ENTITY_NOT_FOUND("btg.entity-not-found"),
    ACCESS_DENIED("btg.access-denied");

    private final String key;

    MessageCode() {
        this.key = name();
    }
}
