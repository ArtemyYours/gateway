package ru.balacetracker.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MessageCode {

    UNKNOWN,
    REST_EXCHANGE_FAILURE("csp.rest-exchange-failure");

    private final String key;

    MessageCode() {
        this.key = name();
    }
}
