package com.solvd.automation.lab.fall.parser;

import com.solvd.automation.lab.fall.responseHandler.*;
import com.solvd.automation.lab.fall.exception.UnknownResponsePattern;

import java.util.ArrayList;
import java.util.List;

public class MyParser {

    public static void parse(String response) throws UnknownResponsePattern {

        System.out.println(response);
        List<String> fields = MyParser.findMessageFields(response);

        if (isFieldsEqualTo(fields, Pattern.LOGIN_RESPONSE_PATTERN)) {

            LogInResponse logInResponse = new LogInResponse(response);
            Thread thread = new Thread(logInResponse);
            thread.start();

        } else if (isFieldsEqualTo(fields, Pattern.CONTACT_RESPONSE_PATTERN)) {

            ContactClientResponse contactClientResponse = new ContactClientResponse(response);
            Thread thread = new Thread(contactClientResponse);
            thread.start();

        } else if (isFieldsEqualTo(fields, Pattern.REGISTRATION_RESPONSE_PATTERN)) {

            RegistrationResponse registrationResponse = new RegistrationResponse(response);
            Thread thread = new Thread(registrationResponse);
            thread.start();

        } else if (isFieldsEqualTo(fields, Pattern.CHECKSUM_SENDER_RESPONSE_PARSER)) {

            ChecksumSenderResponse checksumSenderResponse = new ChecksumSenderResponse(response);
            Thread thread = new Thread(checksumSenderResponse);
            thread.start();

        } else if (isFieldsEqualTo(fields, Pattern.CHECKSUM_GETTER_RESPONSE_PARSER)) {

            CheckSumReceiverResponse checkSumReceiverResponse = new CheckSumReceiverResponse(response);
            Thread thread = new Thread(checkSumReceiverResponse);
            thread.start();

        } else {
            throw new UnknownResponsePattern("Can't determine response pattern");
        }
    }

    private static List<String> findMessageFields(String response) {
        String[] configuration = response.split(",");

        List<String> fields = new ArrayList<>();
        for (int i = 0; i < configuration.length; i++) {
            String keyValuePair = configuration[i];

            int firsIndex = keyValuePair.indexOf('\"') + 1;
            int secondIndex = keyValuePair.indexOf('\"', firsIndex);

            String param = keyValuePair.substring(firsIndex, secondIndex);
            fields.add(param);
        }

        return fields;
    }

    private static boolean isFieldsEqualTo(List<String> fields, Pattern responsePattern) {
        String[] patternFields = responsePattern.getOptions();

        if (fields.size() == patternFields.length) {

            for (int i = 0; i < fields.size(); i++) {
                if (!fields.get(i).equals(patternFields[i])) {
                    return false;
                }
            }

        } else {
            return false;
        }
        return true;
    }

}
