package com.solvd.automation.lab.fall.gui;

import com.solvd.automation.lab.fall.util.UserConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MessengerGui {
    private JFrame frame;
    private JTextArea incoming;
    private JTextField outgoing;
    private JPanel chatPanel = new JPanel();
    private UserConnection userConnection;

    public JFrame createMessengerFrame(String name, UserConnection connection) {

        this.userConnection = connection;
        Thread thread = new Thread(userConnection);
        thread.start();

        frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel(new BorderLayout());

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

        sendPanel.add(outgoing);
        sendPanel.add(sendButton);

        chatPanel.add(incomingScroll);
        chatPanel.add(sendPanel);

        mainPanel.add(chatPanel, BorderLayout.CENTER);

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


}
