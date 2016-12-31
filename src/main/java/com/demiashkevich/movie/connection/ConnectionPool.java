package com.demiashkevich.movie.connection;


import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool{

    private static ArrayBlockingQueue<ProxyConnection> connections;

    private static AtomicBoolean instance = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();

    private ConnectionPool(){
    }

    public static void getInstance(final int POOL_SIZE) {
        if(!instance.get()) {
            try {
                lock.lock();
                if( connections == null) {
                    instance.getAndSet(true);
                    connections = new ArrayBlockingQueue<>(POOL_SIZE);
                    for (int i = 0; i < POOL_SIZE; i++) {
                        CreateConnection connection = new CreateConnection();
                        connections.offer(connection.getConnection());
                        System.out.println("CREATE CONNECTION");
                    }
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public static ProxyConnection takeConnection(){
        try {
            System.out.println("CONNECTION TAKE");
            return connections.take();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
            throw  new RuntimeException(exception);
        }
    }

    public static void putConnection(ProxyConnection connection){
        try {
            System.out.println("CONNECTION PUT");
            connections.put(connection);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void close(){
        final int POOL_SIZE = connections.size();
        for(int i = 0; i < POOL_SIZE; i++){
            ProxyConnection connection;
            try {
                connection = connections.take();
                System.out.println("CONNECTION CLOSE");
                connection.close();
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
