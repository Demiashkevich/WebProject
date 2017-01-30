package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.dao.UserDAO;
import com.demiashkevich.movie.entity.User;
import com.demiashkevich.movie.exception.DAOException;
import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.type.ClientRank;
import com.demiashkevich.movie.validation.UserValidation;
import com.demiashkevich.movie.validation.Validation;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

public class UserService extends AbstractService {

    private static final int BAFTA_INTERVAL_MIN = 0;
    private static final int BAFTA_INTERVAL_MAX = 20;
    private static final int GOLDEN_GLOBES_INTERVAL_MIN = 21;
    private static final int GOLDEN_GLOBES_INTERVAL_MAX = 100;
    private static final int OSCAR_INTERVAL_MIN = 101;

    private static final int ERROR_VALIDATION = -1;
    private static final int ERROR_EXIST = 0;
    private static final int SUCCESS = 1;

    public UserService() {}

    public int addUser(User user) throws ServiceException {
        try {
            connection = ConnectionPool.takeConnection();

            UserDAO userDAO = new UserDAO(connection);
            String email = user.getEmail();
            Validation<User> validation = new UserValidation();
            if (!validation.execute(user)) {
                return ERROR_VALIDATION;
            }
            if (userDAO.findUser(email) != null) {
                return ERROR_EXIST;
            }
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
            userDAO.addItem(user);
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return SUCCESS;
    }

    public User updateRank(User user) throws ServiceException {
        int experience = user.getExperience();
        ClientRank rank = ClientRank.valueOf(user.getRank().toUpperCase());
        UserDAO userDAO = new UserDAO(connection);
        try {
            if(BAFTA_INTERVAL_MIN <= experience && experience <= BAFTA_INTERVAL_MAX){
                if(rank != ClientRank.BAFTA) {
                    userDAO.updateRankUser(user.getUserId(), ClientRank.BAFTA.getNameRank());
                    rank = ClientRank.BAFTA;
                }
            }
            if(GOLDEN_GLOBES_INTERVAL_MIN <= experience && experience <= GOLDEN_GLOBES_INTERVAL_MAX){
               if(rank != ClientRank.GOLDEN_GLOBES){
                   userDAO.updateRankUser(user.getUserId(), ClientRank.GOLDEN_GLOBES.getNameRank());
                   rank = ClientRank.GOLDEN_GLOBES;
               }
            }
            if(OSCAR_INTERVAL_MIN <= experience){
                if(rank != ClientRank.OSCAR){
                    userDAO.updateRankUser(user.getUserId(), ClientRank.OSCAR.getNameRank());
                    rank = ClientRank.OSCAR;
                }
            }
            user.setRank(rank.getNameRank());
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        }
        return user;
    }

    public boolean updateStatusUser(int userId, boolean status) throws ServiceException {
        try {
            connection = ConnectionPool.takeConnection();

            UserDAO userDAO = new UserDAO(connection);
            boolean changeStatus = !status;
            return userDAO.updateStatusUser(userId, changeStatus);
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        } finally {
            ConnectionPool.putConnection(connection);
        }
    }

    public List<User> findUsers() throws ServiceException {
        try {
            connection = ConnectionPool.takeConnection();

            UserDAO userDAO = new UserDAO(connection);
            return userDAO.findAllItems();
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        } finally {
            ConnectionPool.putConnection(connection);
        }
    }

    public User findUser(String email, String password) throws ServiceException {
        try {
            connection = ConnectionPool.takeConnection();

            UserDAO userDAO = new UserDAO(connection);
            User user = userDAO.findUser(email);
            password = DigestUtils.md5Hex(password);
            if (user != null && user.isStatus() && password.equals(user.getPassword())) {
                return user;
            }
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return null;
    }

}
