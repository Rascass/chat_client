package com.solvd.automation.lab.fall.Gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class ClientGui {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientGui.class);
    private static JFrame appFrame;

    public static void main(String[] args) {

        appFrame = new AuthorizationGui().createAuthorizationFrame();
    }

    public static void resetFrameTo(JFrame toFrame){
        appFrame.dispose();
        appFrame = toFrame;
    }
}
