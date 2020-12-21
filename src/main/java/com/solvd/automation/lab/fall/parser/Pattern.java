package com.solvd.automation.lab.fall.parser;

public enum Pattern {
    LOGIN_RESPONSE_PATTERN("code", "logInDescription"),
    CONTACT_RESPONSE_PATTERN("code", "connection"),
    REGISTRATION_RESPONSE_PATTERN("code","regDescription");

    private final String[] options;

    private Pattern(String... options) {
        this.options = options;
    }

    public String[] getOptions() {
        return options;
    }
}
