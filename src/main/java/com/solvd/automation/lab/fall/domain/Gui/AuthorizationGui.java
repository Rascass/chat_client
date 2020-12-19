package com.solvd.automation.lab.fall.domain.Gui;

import com.solvd.automation.lab.fall.util.ServerConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class AuthorizationGui {
    private JPanel authorizationPanel;
    private JTextField userLogin;
    private JPasswordField userPassword;
    private JButton sendButton;
    private JButton registerButton;
    private Thread authorizationThread;
    private ServerConnection serverConnectionRunnable;

    public JPanel getAuthorizationPanel() {

        serverConnectionRunnable = ServerConnection.createAuthorization();
        authorizationThread = new Thread(serverConnectionRunnable);

        authorizationPanel = new JPanel();
        SpringLayout authorizationLayout = new SpringLayout();
        authorizationPanel.setLayout(authorizationLayout);

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
                SpringLayout.NORTH,authorizationPanel);
        authorizationLayout.putConstraint(SpringLayout.NORTH, buttonBox,80,
                SpringLayout.NORTH,authorizationPanel);
        return authorizationPanel;
    }

    public class SendButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            serverConnectionRunnable.setLogin(userLogin.getText());

            int passHash = Objects.hash(String.valueOf(userPassword.getPassword()));
            serverConnectionRunnable.setPassHash(passHash);

            authorizationThread.start();
        }
    }
}
