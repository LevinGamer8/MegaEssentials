package de.megaessentialsrecode.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    private static String host;
    private static int port;
    private static String database;
    private static String username;
    private static String password;
    private static Connection connection;

    private static Connection con;

    public MySQLConnection(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        }

        public static void connect() {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false";
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Connected to MySQL database!");
                con = connection;
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }

        public static void disconnect() {
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Disconnected from MySQL database!");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        public static Connection getConnection() {
            return con;
        }
}
