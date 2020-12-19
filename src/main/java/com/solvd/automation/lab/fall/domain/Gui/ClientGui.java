package com.solvd.automation.lab.fall.domain.Gui;

import javax.swing.*;

public class ClientGui {

    private static JFrame appFrame;

    public static void main(String[] args) {

        appFrame = new AuthorizationGui().getAuthorizationFrame();
    }

    public static void resetFrameTo(JFrame toFrame){
        appFrame.dispose();
        appFrame = toFrame;
    }
}
