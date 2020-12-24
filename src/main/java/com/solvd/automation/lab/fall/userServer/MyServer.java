package com.solvd.automation.lab.fall.userServer;

import com.solvd.automation.lab.fall.gui.ClientGui;
import com.solvd.automation.lab.fall.gui.HubGui;
import com.solvd.automation.lab.fall.util.UserConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class MyServer implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger();

    private Set<ConnectionHandler> connections;
    private int port;
    private HubGui hubGui;
    private final String ip = "127.0.0.1";
    private String login;

    public MyServer(int port, String login) {
        this.port = port;
        this.login = login;
    }

    public void run() {

        LOGGER.info("Starting client server...");
        connections = new HashSet<>();

        try {

            ServerSocket serverSocket = new ServerSocket(port);

            this.createSelfConnection();

            while (true) {
                Socket userSocket = serverSocket.accept();

                ConnectionHandler connection = new ConnectionHandler(userSocket);

                connections.add(connection);

                Thread client = new Thread(connection);
                client.start();

                LOGGER.info("Got a new connection");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void createSelfConnection() {
        LOGGER.info("Creating a connection to your own server with port: " + port);
        UserConnection selfConnection = new UserConnection(ip, port, login, login);

        hubGui = HubGui.getHub();
        JFrame frame = hubGui.createHubFrame("MyHub", selfConnection, login);
        ClientGui.resetFrameTo(frame);
    }

    public void sendEveryOne(String message) {

        LOGGER.info("Printing message to everyone: " + message);

        Iterator<ConnectionHandler> iterator = connections.iterator();
        while (iterator.hasNext()) {

            ConnectionHandler connectionHandler = iterator.next();
            connectionHandler.writer(message);
        }
    }

    public class ConnectionHandler implements Runnable {
        private BufferedReader in;
        private BufferedWriter out;
        private String userLogin;

        public ConnectionHandler(Socket socket) {

            try {

                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                userLogin = in.readLine();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


        @Override
        public void run() {
            String tmp;
            String message;

            try {
                while ((tmp = in.readLine()) != null) {
                    message = tmp;
                    LOGGER.info("Read message: " + message);
                    int checksum = findChecksum(message);

                    LOGGER.info("And here we need somehow compare checksums... Message checksum: " + checksum);
                    sendEveryOne(userLogin + ": " + message);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        public void writer(String message) {
            try {
                out.write(message);
                out.newLine();
                out.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        private int findChecksum(String message) {
            int sum = 0;
            byte[] bytes = message.getBytes();

            for (int i = 0; i < bytes.length; i++) {
                sum += bytes[i];
            }

            return sum;
        }
    }
}
