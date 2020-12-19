package com.solvd.automation.lab.fall.domain.Gui;

import javax.swing.*;

public class ClientGui {

    private static JFrame mainFrame;

    public static void main(String[] args) {

        mainFrame = new JFrame();
        AuthorizationGui authorizationGui = new AuthorizationGui();

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().add(authorizationGui.getAuthorizationPanel());

        mainFrame.setSize(350, 160);
        mainFrame.setVisible(true);
    }
}
