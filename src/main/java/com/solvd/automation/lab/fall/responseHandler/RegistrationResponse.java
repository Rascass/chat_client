package com.solvd.automation.lab.fall.responseHandler;

import com.solvd.automation.lab.fall.Gui.AuthorizationGui;
import com.solvd.automation.lab.fall.Gui.ClientGui;
import com.solvd.automation.lab.fall.Gui.QuickMessageGui;


public class RegistrationResponse implements Runnable {

    private String code;
    private String regDescription;

    public RegistrationResponse(String response) {
        int codeFrom = response.indexOf(":") + 1;
        int codeTo = response.indexOf(",");
        code = response.substring(codeFrom, codeTo);

        int regDescriptionFrom = response.indexOf(":", codeFrom + 1) + 1;
        int regDescriptionTo = response.indexOf("}");
        regDescription = response.substring(regDescriptionFrom, regDescriptionTo);
    }

    @Override
    public void run() {
        QuickMessageGui quickMessageGui = new QuickMessageGui();

        switch (code) {
            case ("\"0\""):
                ClientGui.resetFrameTo(new AuthorizationGui().createAuthorizationFrame());
            case ("\"1\""):
            case ("\"2\""):
                quickMessageGui.go(regDescription + ", with code: " + code);
                break;
            default:
                quickMessageGui.go("wrong code from server");
                break;
        }
    }
}
