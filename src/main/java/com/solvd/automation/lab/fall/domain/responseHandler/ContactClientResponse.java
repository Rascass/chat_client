package com.solvd.automation.lab.fall.domain.responseHandler;


public class ContactClientResponse implements Runnable {
    //message format like {"code":"0","description":session established}
    private String code;
    private String connection;

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
        if (code.equals("\"0\"")) {

        } else if (code.equals("\"-1\"")){

        }
    }
}
