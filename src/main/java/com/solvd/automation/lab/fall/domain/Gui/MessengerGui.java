package com.solvd.automation.lab.fall.domain.Gui;

import com.solvd.automation.lab.fall.util.ServerConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessengerGui {
    JFrame frame;
    JTextField contactLogin;

    public JFrame getMessengerFrame() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel findClientPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(findClientPanel, BoxLayout.X_AXIS);

        contactLogin = new JTextField("Enter your friend's login", 20);
        JButton findButton = new JButton("Find contact");

        findClientPanel.add(contactLogin);
        findClientPanel.add(findButton);
        mainPanel.add(findClientPanel,BorderLayout.SOUTH);

        frame.add(mainPanel);

        frame.setSize(500, 200);
        frame.setVisible(true);
        return frame;
    }

    public class FindButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ServerConnection connection = ServerConnection.getServerConnection();
            connection.findContact(contactLogin.getText());
        }
    }
}
