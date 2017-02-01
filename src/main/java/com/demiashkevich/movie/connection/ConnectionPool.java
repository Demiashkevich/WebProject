package com.demiashkevich.movie.connection;


import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool{

    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);

    private static ArrayBlockingQueue<ProxyConnection> connections;

    private static AtomicBoolean instance = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();

    private ConnectionPool(final int POOL_SIZE){
        connections = new ArrayBlockingQueue<>(POOL_SIZE);
    }

    /**Create connection pool and call method which fill him
     * @param POOL_SIZE
     */
    public static void buildInstance(final int POOL_SIZE) {
        if(!instance.get()) {
            try {
                lock.lock();
                if( connections == null) {
                    instance.getAndSet(true);
                    ConnectionPool connectionPool = new ConnectionPool(POOL_SIZE);
                    connectionPool.fillConnectionPool(POOL_SIZE);
                }
            } finally {
                lock.unlock();
            }
        }
    }

    /**Create connection and fill connection pool
     * @param POOL_SIZE
     */
    private void fillConnectionPool(final int POOL_SIZE){
        for (int i = 0; i < POOL_SIZE; i++) {
            CreateConnection connection = new CreateConnection();
            connections.offer(connection.getConnection());
        }
    }

    /**Take connection from connection pool
     * @return Connection
     */
    public static ProxyConnection takeConnection(){
        ProxyConnection connection = null;
        try {
            connection = connections.take();
        } catch (InterruptedException exception) {
            LOGGER.error(exception);
        }
        return connection;
    }


    /** Put created earlie connection in connection pool
     * @param connection
     */
    public static void putConnection(ProxyConnection connection){
        try {
            connections.put(connection);
        } catch (InterruptedException exception) {
            LOGGER.error(exception);
        }
    }

    /**
     * Destroy ConnectionPool
     */
    public static void close(){
        final int POOL_SIZE = connections.size();
        for(int i = 0; i < POOL_SIZE; i++){
            ProxyConnection connection;
            try {
                connection = connections.take();
                connection.close();
            } catch (SQLException | InterruptedException exception) {
                LOGGER.error(exception);
            }
        }
    }

}
