package com.solvd.automation.lab.fall.util;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Authorization implements Runnable {

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public Authorization(String ip, int port) {
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
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your login and password\nLogin: ");
        String login = scanner.nextLine();
        System.out.print("Password: ");
        int hashPass = Objects.hash(scanner.nextLine());

        this.logIn(login, hashPass);
        this.getResponse();

    }

    private void logIn(String login, int passHash) {
        String message = "{\"login\":\"" + login + "\",\"password\":" + passHash + "}";

        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void getResponse() {
        String response;

        try {
            while (((response = reader.readLine()) != null)) {
                Parser.parse(response);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
