package com.solvd.automation.lab.fall.parser;

public enum Pattern {
    LOGIN_RESPONSE_PATTERN("code", "description"),
    CONTACT_RESPONSE_PATTERN("code", "connection");

    private final String[] options;

    private Pattern(String... options) {
        this.options = options;
    }

    public String[] getOptions() {
        return options;
    }
}
