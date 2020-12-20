package com.solvd.automation.lab.fall.Gui;

import javax.swing.*;

public class ClientGui {

    private static JFrame appFrame;

    public static void main(String[] args) {

        appFrame = new AuthorizationGui().createAuthorizationFrame();
    }

    public static void resetFrameTo(JFrame toFrame){
        appFrame.dispose();
        appFrame = toFrame;
    }
}
