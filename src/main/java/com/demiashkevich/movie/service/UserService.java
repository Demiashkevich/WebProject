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
    public boolean addItem(User user) {
        UserDAO userDAO = new UserDAO(connection);
        String email = user.getEmail();

        if(!userDAO.isFindUser(email)){
            if(validationStrategy.validate(user)) {
                userDAO.addItem(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public User getItem(String key) {
        return null;
    }

    public User getItem(String email, String password) {
        UserDAO userDAO = new UserDAO(connection);
        User user = userDAO.findUser(email, password);
        if (user != null) {
            return user;
        }
        return null;
    }

    @Override
    public List<User> getItems(final int COUNT) {
        return null;
    }

}
