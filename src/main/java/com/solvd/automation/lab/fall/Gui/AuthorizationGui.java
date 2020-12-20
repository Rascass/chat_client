package com.solvd.automation.lab.fall.Gui;

import com.solvd.automation.lab.fall.util.ServerConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class AuthorizationGui {
    private JFrame mainFrame;
    private JPanel authorizationPanel;
    private JTextField userLogin;
    private JPasswordField userPassword;
    private JButton sendButton;
    private JButton registerButton;
    private Thread serverThread;
    private ServerConnection serverConnectionRunnable;

    public JFrame createAuthorizationFrame() {

        serverConnectionRunnable = new ServerConnection();
        serverThread = new Thread(serverConnectionRunnable);
        serverThread.start();

        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        authorizationPanel = new JPanel();
        SpringLayout authorizationLayout = new SpringLayout();
        authorizationPanel.setLayout(authorizationLayout);
        mainFrame.getContentPane().add(authorizationPanel);

        JPanel loginPanel = new JPanel();
        GridLayout loginLayout = new GridLayout(2, 2, 5, 12);
        loginPanel.setLayout(loginLayout);

        Box buttonBox = new Box(BoxLayout.X_AXIS);

        JLabel loginLabel = new JLabel("Login");
        JLabel passwordLabel = new JLabel("Password");

        userLogin = new JTextField(12);
        userPassword = new JPasswordField(12);

        loginPanel.add(loginLabel);
        loginPanel.add(userLogin);
        loginPanel.add(passwordLabel);
        loginPanel.add(userPassword);

        sendButton = new JButton("log in");
        sendButton.addActionListener(new SendButtonActionListener());
        buttonBox.add(sendButton);

        registerButton = new JButton("register");
        buttonBox.add(registerButton);

        authorizationPanel.add(loginPanel);
        authorizationPanel.add(buttonBox);

        authorizationLayout.putConstraint(SpringLayout.NORTH, loginPanel, 10,
                SpringLayout.NORTH, authorizationPanel);
        authorizationLayout.putConstraint(SpringLayout.NORTH, buttonBox, 80,
                SpringLayout.NORTH, authorizationPanel);

        mainFrame.setResizable(false);
        mainFrame.setSize(350, 160);
        mainFrame.setVisible(true);

        return mainFrame;
    }

    public class SendButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(!serverThread.isInterrupted()){
                serverThread.interrupt();
            }
            serverConnectionRunnable = new ServerConnection();
            serverThread = new Thread(serverConnectionRunnable);

            serverConnectionRunnable.setLogin(userLogin.getText());
            int passHash = Objects.hash(String.valueOf(userPassword.getPassword()));
            serverConnectionRunnable.setPassHash(passHash);

            serverThread.start();

            serverConnectionRunnable.logIn();
        }
    }
}
