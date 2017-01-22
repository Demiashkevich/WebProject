package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.dao.UserDAO;
import com.demiashkevich.movie.entity.User;
import com.demiashkevich.movie.validation.UserValidationStrategy;
import com.demiashkevich.movie.validation.ValidationStrategy;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

public class UserService extends AbstractService {

    private ValidationStrategy<User> validationStrategy;

    public UserService(ProxyConnection connection) {
        super(connection);
        validationStrategy = new UserValidationStrategy();
    }

    public boolean addUser(User user) {
        UserDAO userDAO = new UserDAO(connection);
        String email = user.getEmail();
        if(userDAO.findUser(email) == null){
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
//            if(validationStrategy.validate(user)) {
                userDAO.addItem(user);
                return true;
//            }
        }
        return false;
    }

    public boolean updateStatusUser(int userId, boolean status){
        UserDAO userDAO = new UserDAO(connection);
        boolean changeStatus = !status;
        return userDAO.updateStatusUser(userId, changeStatus);
    }

    public List<User> findUsers(){
        UserDAO userDAO = new UserDAO(connection);
        return userDAO.findAllItems();
    }

    public User findUser(String email, String password) {
        UserDAO userDAO = new UserDAO(connection);
        User user = userDAO.findUser(email);
        password = DigestUtils.md5Hex(password);
        if (user != null && user.isStatus() && password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }

    public boolean deleteUser(long itemId) {
        UserDAO userDAO = new UserDAO(connection);
        userDAO.deleteItem(itemId);
        return true;
    }

}
