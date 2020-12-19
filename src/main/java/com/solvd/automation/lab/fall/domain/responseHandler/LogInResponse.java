package com.solvd.automation.lab.fall.domain.responseHandler;

import com.solvd.automation.lab.fall.domain.Gui.QuickMessageGui;

public class LogInResponse implements Runnable {

    private String code;
    private String description;

    public LogInResponse(String response) {
        int codeFrom = response.indexOf(":") + 1;
        int codeTo = response.indexOf(",");
        code = response.substring(codeFrom, codeTo);

        int descriptionFrom = response.indexOf(":", codeFrom + 1) + 1;
        int descriptionTo = response.indexOf("}");
        description = response.substring(descriptionFrom, descriptionTo);
    }

    @Override
    public void run() {
        QuickMessageGui quickMessageGui = new QuickMessageGui();
        quickMessageGui.go(description + ", with code: " + code);
    }
}
