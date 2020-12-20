package com.solvd.automation.lab.fall.Gui;

import com.solvd.automation.lab.fall.util.ServerConnection;
import com.solvd.automation.lab.fall.util.UserConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MessengerGui {
    private JFrame frame;
    private JTextArea incoming;
    private JTextField outgoing;
    private JTextField contactLogin;
    private JPanel chatPanel = new JPanel();
    private UserConnection userConnection;

    public JFrame createMessengerFrame() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel findClientPanel = new JPanel();
        findClientPanel.setLayout(new BoxLayout(findClientPanel, BoxLayout.X_AXIS));

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

        outgoing = new JTextField(20);

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonListener());

        sendPanel.add(outgoing);
        sendPanel.add(sendButton);

        chatPanel.add(incomingScroll);
        chatPanel.add(sendPanel);

        contactLogin = new JTextField("Enter your friend's login", 20);

        JButton findButton = new JButton("Find contact");
        findButton.addActionListener(new FindButtonListener());

        findClientPanel.add(contactLogin);
        findClientPanel.add(findButton);

        chatPanel.setVisible(false);
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
            ServerConnection connection = new ServerConnection();
            Thread thread = new Thread(connection);
            thread.start();

            connection.findContact(contactLogin.getText());
        }
    }

    public void setUpConnection(UserConnection userConnection){
        chatPanel.setVisible(true);
        this.userConnection = userConnection;
    }
}
