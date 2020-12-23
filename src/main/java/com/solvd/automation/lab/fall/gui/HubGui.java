package com.solvd.automation.lab.fall.gui;

import com.solvd.automation.lab.fall.util.ServerConnection;
import com.solvd.automation.lab.fall.util.UserConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HubGui {
    private static HubGui instance = null;
    private JFrame frame;
    private JTextArea incoming;
    private JTextField outgoing;
    private JPanel chatPanel;
    private JTextField contactLogin;
    private UserConnection userConnection;
    private String login;

    private HubGui() {
    }

    public synchronized static HubGui getHub() {
        if (instance == null){
            instance = new HubGui();
        }

        return instance;
    }

    public JFrame createHubFrame(String name, UserConnection connection) {

        this.userConnection = connection;
        Thread thread = new Thread(userConnection);
        thread.start();

        frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel(new BorderLayout());

        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));

        JPanel sendPanel = new JPanel();
        sendPanel.setLayout(new BoxLayout(sendPanel, BoxLayout.X_AXIS));

        incoming = new JTextArea(15, 40);
        incoming.setLineWrap(true);
        incoming.setWrapStyleWord(true);
        incoming.setEditable(false);
        JScrollPane incomingScroll = new JScrollPane(incoming);
        incomingScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        incomingScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        userConnection.setIncoming(incoming);

        outgoing = new JTextField(20);

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonListener());

        JPanel findClientPanel = new JPanel();
        findClientPanel.setLayout(new BoxLayout(findClientPanel, BoxLayout.X_AXIS));

        contactLogin = new JTextField("Enter your friend's login", 20);

        JButton findButton = new JButton("Find contact");
        findButton.addActionListener(new FindButtonListener());

        findClientPanel.add(contactLogin);
        findClientPanel.add(findButton);

        sendPanel.add(outgoing);
        sendPanel.add(sendButton);

        chatPanel.add(incomingScroll);
        chatPanel.add(sendPanel);

        mainPanel.add(chatPanel, BorderLayout.CENTER);
        mainPanel.add(findClientPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);

        frame.setSize(500, 200);
        frame.setVisible(true);
        return frame;
    }

    public class SendButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            userConnection.sendMessageToChat(outgoing.getText());
            outgoing.setText("");
            outgoing.requestFocus();
        }
    }

    public class FindButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ServerConnection connection = ServerConnection.getInstance();

            connection.findContact(contactLogin.getText());
        }
    }
}

