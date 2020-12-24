package com.solvd.automation.lab.fall.parser;

public enum Pattern {
    LOGIN_RESPONSE_PATTERN("code", "logInDescription", "login"),
    CONTACT_RESPONSE_PATTERN("code", "connection", "login"),
    REGISTRATION_RESPONSE_PATTERN("code","regDescription"),
    CHECKSUM_SENDER_RESPONSE_PARSER("code", "checkSumFromDescription"),
    CHECKSUM_GETTER_RESPONSE_PARSER("code", "checkSumToDescription","checkSum");

    private final String[] options;

    private Pattern(String... options) {
        this.options = options;
    }

    public String[] getOptions() {
        return options;
    }
}
