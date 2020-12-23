package com.solvd.automation.lab.fall.userServer;

import com.solvd.automation.lab.fall.gui.MessengerGui;
import com.solvd.automation.lab.fall.util.UserConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class MyServer implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger();

    private Set<ConnectionHandler> connections;
    private int port;
    private MessengerGui messengerGui;
    private final String ip = "127.0.0.1";

    public MyServer(int port) {
        this.port = port;
    }

    public void run() {

        connections = new HashSet<>();

        try {
            this.createSelfConnection();

            ServerSocket serverSocket = new ServerSocket(port);

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
        UserConnection selfConnection = new UserConnection(ip, port, "my server");

        messengerGui = new MessengerGui();
        messengerGui.createMessengerFrame("My hub", selfConnection);
    }

    public void sendEveryOne(String message) {

        Iterator<ConnectionHandler> iterator = connections.iterator();
        while (iterator.hasNext()) {

            ConnectionHandler connectionHandler = iterator.next();
            connectionHandler.writer(message);
        }
    }


    public class ConnectionHandler implements Runnable {
        private BufferedReader in;
        private BufferedWriter out;

        public ConnectionHandler(Socket socket) {
            try {

                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

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
                    sendEveryOne(message);
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
    }
}
