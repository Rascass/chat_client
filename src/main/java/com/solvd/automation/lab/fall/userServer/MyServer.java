package com.solvd.automation.lab.fall.userServer;

import com.solvd.automation.lab.fall.Gui.MessengerGui;
import com.solvd.automation.lab.fall.util.UserConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger();

    private List<BufferedWriter> userOutputStreams;
    private int port;
    private JFrame serverFrame;
    private final String ip = "127.0.0.1";

    public MyServer(int port) {
        this.port = port;
    }

    public void run() {

        userOutputStreams = new ArrayList<>();

        try {
            MessengerGui messengerGui = new MessengerGui();
            serverFrame = messengerGui.createMessengerFrame("Connection to my own server");

            LOGGER.info("Creating a connection to your own server with port: " + port);
            UserConnection selfConnectionServer = new UserConnection(ip, port, messengerGui.getIncoming());
            messengerGui.setUpConnection(selfConnectionServer);

            ServerSocket serverSocket = new ServerSocket(port);

            while (true) {
                Socket userSocket = serverSocket.accept();

                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(userSocket.getOutputStream()));

                userOutputStreams.add(out);

                Thread client = new Thread(new UserHandler(userSocket));
                client.start();

                LOGGER.info("Got a connection");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void sendEveryOne(String message) {
        for (BufferedWriter writer : userOutputStreams) {
            try {
                writer.write(message);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    public class UserHandler implements Runnable {
        private BufferedReader in;
        private Socket userSocket;

        public UserHandler(Socket socket) {
            try {
                this.userSocket = socket;

                in = new BufferedReader(new InputStreamReader(userSocket.getInputStream()));
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
                    sendEveryOne(message);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
