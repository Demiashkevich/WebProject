package com.demiashkevich.movie.dao;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.User;
import com.demiashkevich.movie.exception.DAOException;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserDAOCheck {

    private static final Logger LOGGER = Logger.getLogger(UserDAO.class);

    @BeforeClass
    public static void initConnectionPool(){
        ConnectionPool.buildInstance(5);
    }

    @AfterClass
    public static void destroyConnectionPool(){
        ConnectionPool.close();
    }

    @Test
    public void findUserCheck(){
        ProxyConnection connection = ConnectionPool.takeConnection();
        try {
            UserDAO userDAO = new UserDAO(connection);

            User userExpected = userDAO.findUser("DemEgor");
            Assert.assertNull(userExpected);
        } catch (DAOException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.putConnection(connection);
        }
    }

}
