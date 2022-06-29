package by.mishastoma.libraryweb.controller.pool;

import com.mysql.cj.jdbc.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static final Logger logger = LogManager.getLogger();
    public static final String CONFIG_FILE_NAME = "pool_config.properties";
    private static final int DEFAULT_POOL_SIZE = 8;
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static final ReentrantLock lock = new ReentrantLock(true);
    private static final Properties properties;
    private static final String URL_KEY = "url";
    private static ConnectionPool instance;
    private BlockingQueue<ProxyConnection> free = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
    private BlockingQueue<ProxyConnection> used = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);


    static {
        properties = new Properties();
        try (InputStream inputStream = ConnectionPool.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME)) {
            properties.load(inputStream);
            DriverManager.registerDriver(new Driver());
        } catch (IOException | SQLException e) {
            throw new ExceptionInInitializerError(e.getMessage());
        }
    }

    private ConnectionPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                Connection connection = DriverManager.getConnection(properties.getProperty(URL_KEY), properties);
                free.add(new ProxyConnection(connection));
            } catch (SQLException e) {
                logger.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    public static ConnectionPool getInstance() {
        if (!isCreated.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    isCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = free.take();
            used.put(connection);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public boolean releaseConnection(Connection connection) {
        boolean isRemoved = false;
        if (connection.getClass() == ProxyConnection.class) {
            ProxyConnection proxyConnection = (ProxyConnection) connection;
            isRemoved = used.remove(proxyConnection);
            if (isRemoved) {
                try {
                    free.put(proxyConnection);

                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                    Thread.currentThread().interrupt();
                }
            }
        }
        return isRemoved;
    }

    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                free.take().actualClose();
            } catch (SQLException | InterruptedException e) {
                logger.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        });
    }
}
