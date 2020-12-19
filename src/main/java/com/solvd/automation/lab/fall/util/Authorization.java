package com.solvd.automation.lab.fall.util;

import com.solvd.automation.lab.fall.constant.PropertyConstant;
import com.solvd.automation.lab.fall.domain.parser.Parser;
import com.solvd.automation.lab.fall.exception.UnknownResponsePattern;
import com.solvd.automation.lab.fall.io.PropertyReader;

import java.io.*;
import java.net.Socket;


public class Authorization implements Runnable {

    private static Authorization instance = null;
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String login;
    private int passHash;

    private Authorization() {

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

    public static Authorization createAuthorization() {
        if (instance == null) {
            instance = new Authorization();
        }

        return instance;
    }

    @Override
    public void run() {

        this.logIn();
        this.getResponse();

    }

    private void logIn() {
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
        } catch (UnknownResponsePattern urp) {
            urp.printStackTrace();
        }
    }

    public void close(){
        try {
            reader.close();
            writer.close();
            socket.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassHash(int passHash) {
        this.passHash = passHash;
    }
}
