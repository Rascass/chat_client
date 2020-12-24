package com.solvd.automation.lab.fall.gui;

import com.solvd.automation.lab.fall.util.ServerConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class RegisterGui {
    private JFrame mainFrame;
    private JPanel authorizationPanel;
    private JTextField userLogin;
    private JPasswordField userPassword;
    private JPasswordField repeatPassword;
    private JButton registerButton;
    private JButton logInButton;

    public JFrame createRegistrationFrame() {

        mainFrame = new JFrame("Registration");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        authorizationPanel = new JPanel();
        SpringLayout authorizationLayout = new SpringLayout();
        authorizationPanel.setLayout(authorizationLayout);
        mainFrame.getContentPane().add(authorizationPanel);

        JPanel loginPanel = new JPanel();
        GridLayout loginLayout = new GridLayout(3, 2, 5, 12);
        loginPanel.setLayout(loginLayout);

        Box buttonBox = new Box(BoxLayout.X_AXIS);

        JLabel loginLabel = new JLabel("Login");
        JLabel passwordLabel = new JLabel("Password");
        JLabel repeatPasswordLabel = new JLabel("Repeat password");

        userLogin = new JTextField(12);
        userPassword = new JPasswordField(12);
        repeatPassword = new JPasswordField(12);

        loginPanel.add(loginLabel);
        loginPanel.add(userLogin);
        loginPanel.add(passwordLabel);
        loginPanel.add(userPassword);
        loginPanel.add(repeatPasswordLabel);
        loginPanel.add(repeatPassword);

        registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterButtonListener());
        buttonBox.add(registerButton);

        logInButton = new JButton("Log in");
        logInButton.addActionListener(new LogInButtonListener());
        buttonBox.add(logInButton);

        authorizationPanel.add(loginPanel);
        authorizationPanel.add(buttonBox);

        authorizationLayout.putConstraint(SpringLayout.NORTH, loginPanel, 10,
                SpringLayout.NORTH, authorizationPanel);
        authorizationLayout.putConstraint(SpringLayout.NORTH, buttonBox, 140,
                SpringLayout.NORTH, authorizationPanel);

        mainFrame.setResizable(false);
        mainFrame.setSize(350, 210);
        mainFrame.setVisible(true);

        return mainFrame;
    }

    public class RegisterButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {


            int pass1 = Objects.hash(String.valueOf(userPassword.getPassword()));
            int pass2 = Objects.hash(String.valueOf(repeatPassword.getPassword()));

            if (pass1 == pass2) {

                String login = userLogin.getText();
                ServerConnection connection = ServerConnection.getInstance();

                connection.register(login, String.valueOf(pass1));

            } else {
                new QuickMessageGui().go("Passwords don't match");
            }
        }
    }

    public class LogInButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ClientGui.resetFrameTo(new AuthorizationGui().createAuthorizationFrame());
        }
    }
}

