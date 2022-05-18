package by.mishastoma.libraryweb.controller.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ConnectionPool {
    private static final int DEFAULT_POOL_SIZE = 8;
    private static ConnectionPool instance;
    private BlockingQueue<Connection> free = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
    private BlockingQueue<Connection> used = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);


    static {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver()); // TODO: 11.04.2022  Only once
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private ConnectionPool() {
        String url = "jdbc:mysql://localhost:3306/final_project";
        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "qwerty4");
        properties.put("autoReconnect", "true");
        properties.put("useUnicode", "true");
        properties.put("characterEncoding", "UTF-8");
        properties.put("useJDBCCompliantTimezoneShift", "true");
        properties.put("useLegacyDateTimeCode", "false");
        properties.put("serverTimezone", "UTC");
        properties.put("ServerSslCert", "classpath:server.crt");
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(url, properties);
            } catch (SQLException e) {
                //log todo
                Thread.currentThread().interrupt();
            }
            free.add(connection);
        }
    }
    // deregisterDriver todo
    public static ConnectionPool getInstance() {
        //lock
        instance = new ConnectionPool();
        //unlock
        return instance;
    }
    public Connection getConnection(){
        Connection connection = null;
        try {
            connection = free.take();
            used.put(connection);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void releaseConnection(Connection connection){
        try {
            used.remove(connection);
            free.put(connection);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void destroyPool(){
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                free.take().close();
            } catch (SQLException | InterruptedException e) {
                // log e.printStackTrace(); todo
            }
        }
    }
}
