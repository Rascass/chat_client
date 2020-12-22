package com.solvd.automation.lab.fall.Gui;

import com.solvd.automation.lab.fall.util.ServerConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FindClientGui {

    private static FindClientGui instance = null;
    private JFrame frame;
    private JTextField contactLogin;

    private FindClientGui() {
        this.createGui();
    }

    public static synchronized FindClientGui getFindClientGui() {
        if (instance == null) {
            instance = new FindClientGui();
        }
        return instance;
    }

    private void createGui() {
        frame = new JFrame();
        JPanel findClientPanel = new JPanel();
        findClientPanel.setLayout(new BoxLayout(findClientPanel, BoxLayout.Y_AXIS));

        contactLogin = new JTextField("Enter your friend's login", 20);

        JButton findButton = new JButton("Find contact");
        findButton.addActionListener(new FindButtonListener());

        findClientPanel.add(contactLogin);
        findClientPanel.add(findButton);

        frame.add(findClientPanel);
        frame.setSize(200, 100);
        frame.setVisible(true);

    }

    public JFrame getFrame() {
        return frame;
    }

    public class FindButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ServerConnection connection = ServerConnection.getInstance();

            connection.findContact(contactLogin.getText());
        }
    }
}
