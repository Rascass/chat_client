package com.solvd.automation.lab.fall.responseHandler;


import com.solvd.automation.lab.fall.Gui.ClientGui;
import com.solvd.automation.lab.fall.Gui.MessengerGui;
import com.solvd.automation.lab.fall.Gui.QuickMessageGui;
import com.solvd.automation.lab.fall.util.UserConnection;

public class ContactClientResponse implements Runnable {

    private String code;
    private String connection;
    private String port;
    MessengerGui messengerGui;

    public ContactClientResponse(String response) {
        int codeFrom = response.indexOf(":") + 1;
        int codeTo = response.indexOf(",");
        code = response.substring(codeFrom, codeTo);

        int connectionFrom = response.indexOf(":", codeFrom + 1) + 1;
        int connectionTo = response.indexOf("}");
        connection = response.substring(connectionFrom, connectionTo);
    }

    @Override
    public void run() {
        QuickMessageGui quickMessageGui = new QuickMessageGui();

        switch (code) {
            case ("\"0\""):
                String ip = connection.substring(0, connection.indexOf(":"));
                String port = connection.substring(connection.indexOf(":") + 1);

                System.out.println(ip);
                System.out.println(port);

                UserConnection userConnection = new UserConnection(ip, port);

                messengerGui = new MessengerGui();
                messengerGui.setUpConnection(userConnection);
                ClientGui.resetFrameTo(messengerGui.createMessengerFrame());

                break;
            case ("\"1\""):
            case ("\"2\""):
                quickMessageGui.go(connection + ", with code: " + code);
                break;
            default:
                quickMessageGui.go("wrong code from server");
                break;
        }
    }
}
