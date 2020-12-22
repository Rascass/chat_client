package com.solvd.automation.lab.fall.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class UserConnection implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger();
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private JTextArea incoming;
    private String ip;
    private int port;

    public UserConnection(String ip, int port, JTextArea in) {
        this.ip = ip;
        this.port = port;
        this.incoming = in;
    }

    @Override
    public void run() {
        String message;

        try {
            socket = new Socket(ip, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            MessageListener messageListener = new MessageListener();
            Thread thread = new Thread(messageListener);
            thread.start();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private class MessageListener implements Runnable {

        private String message;

        @Override
        public void run() {
            try {
                while ((message = reader.readLine()) != null) {
                    LOGGER.info("Sending message to chat: " + message);
                    incoming.append(message);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void sendMessageToChat(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
