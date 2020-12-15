package com.solvd.automation.lab.fall.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlite.JDBC;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqliteHandler.class);

    private static final String PROJECT_PATH = new File("").getAbsolutePath();
    private static final String CONNECTION_PATH = "jdbc:sqlite:" + PROJECT_PATH + "\\database\\clientsDB.db";

    private static SqliteHandler instance = null;
    private Connection connection;

    public static synchronized SqliteHandler getInstance() throws SQLException {
        if (instance == null) {
            LOGGER.info(CONNECTION_PATH);
            instance = new SqliteHandler();
        }
        return instance;
    }

    private SqliteHandler() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(CONNECTION_PATH);
    }

    public Connection getConnection() {
        return connection;
    }

}
