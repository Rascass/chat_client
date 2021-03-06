package com.solvd.automation.lab.fall.util;

import com.solvd.automation.lab.fall.constant.PropertyConstant;
import com.solvd.automation.lab.fall.parser.Parser;
import com.solvd.automation.lab.fall.exception.UnknownResponsePattern;
import com.solvd.automation.lab.fall.io.PropertyReader;

import java.io.*;
import java.net.Socket;


public class ServerConnection implements Runnable {

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public ServerConnection() {

        String ip = PropertyReader.getInstance().getValue(PropertyConstant.IP_KEY);
        int port = Integer.parseInt(PropertyReader.getInstance().getValue(PropertyConstant.PORT_KEY));

        try {
            socket = new Socket(ip, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void run() {
        this.serverResponseListener();
    }

    public void logIn(String login, String passHash) {
        String message = "{\"login\":\"" + login + "\",\"password\":" + passHash + "}";

        sendMessageToServer(message);
    }

    public void findContact(String contactLogin) {
        String message = "{\"contactLogin\":\"" + contactLogin + "\"}";

        sendMessageToServer(message);
    }

    public void register(String login, String passHash) {
        String message = "{\"regLogin\":\"" + login + "\",\"regPassword\":" + passHash + "}";

        System.out.println("message" + message);
        sendMessageToServer(message);
    }

    private void serverResponseListener() {
        String response;

        try {
            while (((response = reader.readLine()) != null)) {
                Parser.parse(response);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (UnknownResponsePattern urp) {
            urp.printStackTrace();
        }
    }

    public void sendMessageToServer(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void close() {
        try {
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
