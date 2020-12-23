package com.solvd.automation.lab.fall.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class UserConnection implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger();
    private BufferedReader reader;
    private BufferedWriter writer;
    private JTextArea incoming;
    private final String ip;
    private final int port;
    private String loginTo;

    public UserConnection(String ip, int port, String loginTo) {
        this.ip = ip;
        this.port = port;
        this.loginTo = loginTo;
    }

    @Override
    public void run() {

        try {
            Socket socket = new Socket(ip, port);
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

        @Override
        public void run() {
            try {

                String message;

                while ((message = reader.readLine()) != null) {
                    LOGGER.info("Getting message from server: " + message);
                    incoming.append(message);
                    incoming.append("\n");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void sendMessageToChat(String message) {
        try {
            LOGGER.info("Sending message to server");

            int checksum = this.findChecksum(message);

            ServerConnection.getInstance().sendChecksum("Dima", "Anna", checksum);
            this.write(checksum);
            this.write(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void write(String message) throws IOException {
        writer.write(message);
        writer.newLine();
        writer.flush();
    }

    private void write(int message) throws IOException {
        writer.write(message);
        writer.newLine();
        writer.flush();
    }

    private int findChecksum(String message) {
        int sum = 0;
        byte[] bytes = message.getBytes();

        for (int i = 0; i < bytes.length; i++) {
            sum += bytes[i];
        }

        return sum;
    }

    public void setIncoming(JTextArea incoming) {
        this.incoming = incoming;
    }
}
