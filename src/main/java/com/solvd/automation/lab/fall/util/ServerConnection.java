package com.solvd.automation.lab.fall.util;

import com.solvd.automation.lab.fall.constant.PropertyConstant;
import com.solvd.automation.lab.fall.parser.MyParser;
import com.solvd.automation.lab.fall.exception.UnknownResponsePattern;
import com.solvd.automation.lab.fall.io.PropertyReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;


public class ServerConnection {

    private static ServerConnection instance = null;
    private Socket socket;
    private static final Logger LOGGER = LogManager.getLogger();
    private BufferedWriter writer;

    public static synchronized ServerConnection getInstance() {

        if (instance == null) {
            instance = new ServerConnection();
        }

        return instance;
    }

    private ServerConnection() {

        String ip = PropertyReader.getInstance().getValue(PropertyConstant.IP_KEY);
        int port = Integer.parseInt(PropertyReader.getInstance().getValue(PropertyConstant.PORT_KEY));

        try {
            socket = new Socket(ip, port);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.createServerResponseListener();

            LOGGER.info("Clients address: " + socket.getLocalPort());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void logIn(String login, String passHash) {
        String message = "{\"login\":\"" + login + "\",\"password\":" + passHash + "}";

        LOGGER.info("logIn message" + message);
        sendMessageToServer(message);
    }

    public void findContact(String contactLogin) {
        String message = "{\"contactLogin\":\"" + contactLogin + "\"}";

        LOGGER.info("findContact message" + message);
        sendMessageToServer(message);
    }

    public void register(String login, String passHash) {
        String message = "{\"regLogin\":\"" + login + "\",\"regPassword\":" + passHash + "}";

        LOGGER.info("register message" + message);
        sendMessageToServer(message);
    }

    private void sendMessageToServer(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int getYourOwnPort(){
        return socket.getLocalPort();
    }

    public class ServerResponseListener implements Runnable {

        @Override
        public void run() {
            String response;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
                while (((response = reader.readLine()) != null)) {
                    MyParser.parse(response);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (UnknownResponsePattern urp) {
                urp.printStackTrace();
            }
        }
    }

    public void createServerResponseListener() {
        ServerResponseListener serverResponseListener = new ServerResponseListener();
        Thread thread = new Thread(serverResponseListener);
        thread.start();
    }

    public void closeSocket() {
        try {
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
