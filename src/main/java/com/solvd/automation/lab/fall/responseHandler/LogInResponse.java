package com.solvd.automation.lab.fall.responseHandler;

import com.solvd.automation.lab.fall.Gui.ClientGui;
import com.solvd.automation.lab.fall.Gui.MessengerGui;
import com.solvd.automation.lab.fall.Gui.QuickMessageGui;
import com.solvd.automation.lab.fall.userServer.MyServer;

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


        switch (code) {
            case ("\"0\""):
                ClientGui.resetFrameTo(new MessengerGui().createMessengerFrame());

                MyServer server = new MyServer();
                Thread serverThread = new Thread(server);
                serverThread.start();

                quickMessageGui.go(description + ", with code: " + code);
                break;

            case ("\"1\""):
            case ("\"2\""):
                quickMessageGui.go(description + ", with code: " + code);
                break;
            default:
                quickMessageGui.go("wrong code from server");
                break;
        }

    }
}
