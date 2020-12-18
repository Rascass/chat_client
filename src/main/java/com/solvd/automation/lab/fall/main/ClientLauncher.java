package com.solvd.automation.lab.fall.main;

import com.solvd.automation.lab.fall.util.Authorization;

public class ClientLauncher {

    public static void main(String[] args) {

        Thread authorization =new Thread(new Authorization("127.0.0.1", 8000));
        authorization.start();
    }

}
