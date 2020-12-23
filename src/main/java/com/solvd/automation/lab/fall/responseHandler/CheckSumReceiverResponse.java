package com.solvd.automation.lab.fall.responseHandler;

import com.solvd.automation.lab.fall.gui.AuthorizationGui;
import com.solvd.automation.lab.fall.gui.ClientGui;
import com.solvd.automation.lab.fall.gui.QuickMessageGui;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheckSumReceiverResponse implements Runnable {

    private String code;
    private String checkSumFromDescription;
    private int checksum;
    private static final Logger LOGGER = LogManager.getLogger();

    public CheckSumReceiverResponse(String response) {
        int codeFrom = response.indexOf(":") + 1;
        int codeTo = response.indexOf(",");
        code = response.substring(codeFrom, codeTo);

        int checkSumFromDescriptionFrom = response.indexOf(":", codeFrom + 1) + 1;
        int checkSumFromDescriptionTo = response.indexOf(",", codeTo + 1);
        checkSumFromDescription = response.substring(checkSumFromDescriptionFrom, checkSumFromDescriptionTo);

        int checksumFrom = response.indexOf(":", checkSumFromDescriptionFrom + 1) + 1;
        int checksumTo = response.indexOf("}");
        checksum = Integer.parseInt(response.substring(checksumFrom, checksumTo));
    }

    @Override
    public void run() {
        QuickMessageGui quickMessageGui = new QuickMessageGui();

        switch (code) {
            case ("\"0\""):
                LOGGER.info("Checksum: " + checksum);
            case ("\"1\""):
            case ("\"2\""):
            case ("\"3\""):
                quickMessageGui.go(checkSumFromDescription + ", with code: " + code);
                break;
            default:
                quickMessageGui.go("wrong code from server");
                break;
        }
    }
}
