package com.solvd.automation.lab.fall.gui;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

public class ClientGui {

    private static final Logger LOGGER = LogManager.getLogger();
    private static JFrame appFrame;

    public static void main(String[] args) {
        LOGGER.info("Starting client");
        appFrame = new AuthorizationGui().createAuthorizationFrame();
    }

    public static void resetFrameTo(JFrame toFrame){
        appFrame.dispose();
        appFrame = toFrame;
    }
}
