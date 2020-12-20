package com.solvd.automation.lab.fall.responseHandler;


import com.solvd.automation.lab.fall.Gui.ClientGui;
import com.solvd.automation.lab.fall.Gui.MessengerGui;
import com.solvd.automation.lab.fall.util.UserConnection;

public class ContactClientResponse implements Runnable {
    //message format like {"code":"0","description":session established}
    private String code;
    private String connection;
    MessengerGui messengerGui;

    public ContactClientResponse(String response) {
        int codeFrom = response.indexOf(":") + 1;
        int codeTo = response.indexOf(",");
        code = response.substring(codeFrom, codeTo);

        int connectionFrom = response.indexOf(":", codeFrom + 1) + 1;
        int connectionTo = response.indexOf("}");
        connection = response.substring(connectionFrom, connectionTo);
        connection = "127.0.0.1";
    }

    @Override
    public void run() {
        if (code.equals("\"0\"")) {
            UserConnection userConnection = new UserConnection(connection);

            messengerGui = new MessengerGui();
            messengerGui.setUpConnection(userConnection);
            ClientGui.resetFrameTo(messengerGui.createMessengerFrame());

        } else if (code.equals("\"-1\"")) {

        }
    }
}
