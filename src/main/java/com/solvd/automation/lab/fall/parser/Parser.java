package com.solvd.automation.lab.fall.parser;

import com.solvd.automation.lab.fall.responseHandler.ContactClientResponse;
import com.solvd.automation.lab.fall.responseHandler.LogInResponse;
import com.solvd.automation.lab.fall.exception.UnknownResponsePattern;
import com.solvd.automation.lab.fall.responseHandler.RegistrationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class Parser {

    public static void parse(String response) throws UnknownResponsePattern {

        System.out.println(response);
        List<String> fields = Parser.findMessageFields(response);

        if (isFieldsEqualTo(fields, Pattern.LOGIN_RESPONSE_PATTERN)) {

            LogInResponse logInResponse = new LogInResponse(response);
            Thread thread = new Thread(logInResponse);
            thread.start();

        } else if (isFieldsEqualTo(fields, Pattern.CONTACT_RESPONSE_PATTERN)) {

            ContactClientResponse contactClientResponse = new ContactClientResponse(response);
            Thread thread = new Thread(contactClientResponse);
            thread.start();

        } else if(isFieldsEqualTo(fields, Pattern.REGISTRATION_RESPONSE_PATTERN)){

            RegistrationResponse registrationResponse = new RegistrationResponse(response);
            Thread thread = new Thread(registrationResponse);
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
