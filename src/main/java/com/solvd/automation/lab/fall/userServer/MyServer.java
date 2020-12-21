package com.solvd.automation.lab.fall.userServer;

import com.solvd.automation.lab.fall.Gui.MessengerGui;
import com.solvd.automation.lab.fall.constant.PropertyConstant;
import com.solvd.automation.lab.fall.io.PropertyReader;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer implements Runnable {

    private List<BufferedWriter> userOutputStreams;

    public void run() {

        int port = Integer.parseInt(PropertyReader.getInstance().getValue(PropertyConstant.USER_PORT_KEY));
        userOutputStreams = new ArrayList<>();

        try {
            ServerSocket serverSocket = new ServerSocket(8002);
            while (true) {
                Socket userSocket = serverSocket.accept();
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(userSocket.getOutputStream()));
                userOutputStreams.add(out);

                Thread client = new Thread(new UserHandler(userSocket));
                client.start();

                System.out.println("Got a connection");
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
