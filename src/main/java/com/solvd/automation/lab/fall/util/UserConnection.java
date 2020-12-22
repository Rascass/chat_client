package com.solvd.automation.lab.fall.util;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class UserConnection implements Runnable {

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private JTextArea incoming;

    public UserConnection(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void setIncoming(JTextArea incoming) {
        this.incoming = incoming;
    }

    @Override
    public void run() {
        String message;

        try {
            while ((message = reader.readLine()) != null) {
                System.out.println(message);
                incoming.append(message);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
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
