package com.solvd.automation.lab.fall.util;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static void parse(String response) {
        String[] configuration = response.split(",");

        List<String> fields = new ArrayList<>();
        for (int i = 0; i < configuration.length; i++) {
            String keyValuePair = configuration[i];

            int firsIndex = keyValuePair.indexOf('\"')+1;
            int secondIndex = keyValuePair.indexOf('\"', firsIndex);

            String param = keyValuePair.substring(firsIndex, secondIndex);
            fields.add(param);
            System.out.println(param);
        }
    }
}
