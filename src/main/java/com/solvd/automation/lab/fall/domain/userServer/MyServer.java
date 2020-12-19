package com.solvd.automation.lab.fall.domain.userServer;

import com.solvd.automation.lab.fall.constant.PropertyConstant;
import com.solvd.automation.lab.fall.io.PropertyReader;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer implements Runnable {

    private Socket socket;
    private List<ChatHandler> chats;

    public void run() {


            int port = Integer.parseInt(PropertyReader.getInstance().getValue(PropertyConstant.USER_PORT_KEY));

            try {
                ServerSocket serverSocket = new ServerSocket(port);
                while (true) {
                    socket = serverSocket.accept();

                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

    }

    public class ChatHandler implements Runnable {
        private List<BufferedWriter> userOutputStreams;

        @Override
        public void run() {
            userOutputStreams = new ArrayList<>();
        }
    }
}
