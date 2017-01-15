package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.dao.UserDAO;
import com.demiashkevich.movie.entity.User;
import com.demiashkevich.movie.validation.UserValidationStrategy;
import com.demiashkevich.movie.validation.ValidationStrategy;

import java.util.List;

public class UserService extends AbstractService <User, String> {

    private ValidationStrategy<User> validationStrategy;

    public UserService(ProxyConnection connection) {
        super(connection);
        validationStrategy = new UserValidationStrategy();
    }

    @Override
    public List<User> findAllItem() {
        return null;
    }

    @Override
    public boolean addItem(User user) {
        UserDAO userDAO = new UserDAO(connection);
        String email = user.getEmail();

        if(userDAO.findUser(email) != null){
            if(validationStrategy.validate(user)) {
                userDAO.addItem(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public User findItem(String key) {
        return null;
    }

    public User findItem(String email, String password) {
        UserDAO userDAO = new UserDAO(connection);
        User user = userDAO.findUser(email);
        if (user != null && password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public List<User> findItems(final int COUNT) {
        return null;
    }

    @Override
    public boolean deleteItem(long itemId) {
        UserDAO userDAO = new UserDAO(connection);
        userDAO.deleteItem(itemId);
        return true;
    }

}
