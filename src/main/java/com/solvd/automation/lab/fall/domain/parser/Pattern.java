package com.solvd.automation.lab.fall.domain.parser;

public enum Pattern {
    LOGIN_RESPONSE_PATTERN("code", "description"),
    SESSION_RESPONSE_PATTERN("code", "connection");

    private final String[] options;

    private Pattern(String... options) {
        this.options = options;
    }

    public String[] getOptions() {
        return options;
    }
}
