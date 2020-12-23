package com.solvd.automation.lab.fall.responseHandler;

import com.solvd.automation.lab.fall.gui.ClientGui;
import com.solvd.automation.lab.fall.gui.FindClientGui;
import com.solvd.automation.lab.fall.gui.QuickMessageGui;
import com.solvd.automation.lab.fall.constant.PropertyConstant;
import com.solvd.automation.lab.fall.io.PropertyReader;
import com.solvd.automation.lab.fall.userServer.MyServer;
import com.solvd.automation.lab.fall.util.ServerConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogInResponse implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger();

    private String code;
    private String description;
    private String login;

    public LogInResponse(String response) {

        int codeFrom = response.indexOf(":") + 1;
        int codeTo = response.indexOf(",");
        code = response.substring(codeFrom, codeTo);

        int descriptionFrom = response.indexOf(":", codeFrom + 1) + 1;
        int descriptionTo = response.indexOf(",", codeTo + 1);
        description = response.substring(descriptionFrom, descriptionTo);

        int loginFrom = response.indexOf(":", descriptionFrom + 1) + 1;
        int loginTo = response.indexOf("}");
        login = response.substring(loginFrom, loginTo);
    }

    @Override
    public void run() {

        QuickMessageGui quickMessageGui = new QuickMessageGui();

        switch (code) {
            case ("\"0\""):

                FindClientGui findClientGui = FindClientGui.getFindClientGui();
                ServerConnection connection = ServerConnection.getInstance();

                ClientGui.resetFrameTo(findClientGui.getFrame());

                int port = connection.getYourOwnPort() +
                        Integer.parseInt(PropertyReader.getInstance().getValue(PropertyConstant.MAGIC_NUMBER));

                MyServer server = new MyServer(port);
                Thread serverThread = new Thread(server);
                serverThread.start();


                LOGGER.info("Port in use: " + connection.getYourOwnPort());
                LOGGER.info("Creating client's own server with port: " + port);


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
