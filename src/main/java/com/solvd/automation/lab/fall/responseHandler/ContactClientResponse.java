package com.solvd.automation.lab.fall.responseHandler;


import com.solvd.automation.lab.fall.gui.HubGui;
import com.solvd.automation.lab.fall.gui.MessengerGui;
import com.solvd.automation.lab.fall.gui.QuickMessageGui;
import com.solvd.automation.lab.fall.constant.PropertyConstant;
import com.solvd.automation.lab.fall.io.PropertyReader;
import com.solvd.automation.lab.fall.util.UserConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContactClientResponse implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger();

    private String code;
    private String connection;
    private MessengerGui messengerGui;
    private String login;

    public ContactClientResponse(String response) {
        int codeFrom = response.indexOf(":") + 1;
        int codeTo = response.indexOf(",");
        code = response.substring(codeFrom, codeTo);

        int connectionFrom = response.indexOf(":", codeFrom + 1) + 1;
        int connectionTo = response.indexOf(",", codeTo + 1);
        connection = response.substring(connectionFrom, connectionTo);

        int loginFrom = response.indexOf(":", connectionTo + 1) + 1;
        int loginTo = response.indexOf("}");
        login = response.substring(loginFrom, loginTo);

        LOGGER.info("Login: " + login);
    }

    @Override
    public void run() {
        QuickMessageGui quickMessageGui = new QuickMessageGui();

        switch (code) {
            case ("\"0\""):
                String ip = connection.substring(0, connection.indexOf(":"));
                int port = Integer.parseInt(connection.substring(connection.indexOf(":") + 1))
                        + Integer.parseInt(PropertyReader.getInstance().getValue(PropertyConstant.MAGIC_NUMBER));

                LOGGER.info("find client IP: " + ip);
                LOGGER.info("find client Port: " + port);

                String loginFrom = HubGui.getHub().getLogin();

                UserConnection userConnection = new UserConnection(ip, port, login, loginFrom);

                messengerGui = new MessengerGui();
                messengerGui.createMessengerFrame(userConnection, login);

                break;
            case ("\"1\""):
            case ("\"2\""):
            case ("\"3\""):
                quickMessageGui.go(connection + ", with code: " + code);
                break;
            default:
                quickMessageGui.go("wrong code from server");
                break;
        }
    }
}
