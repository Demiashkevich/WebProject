package com.demiashkevich.movie.listener;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.manager.ConfigurationManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ConnectionPoolListener implements ServletContextListener {

    private final static String POOL_SIZE = "connection.pool.size";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        final int SIZE = Integer.parseInt(ConfigurationManager.getKey(POOL_SIZE));
        ConnectionPool.buildInstance(10);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool.close();
    }

}
