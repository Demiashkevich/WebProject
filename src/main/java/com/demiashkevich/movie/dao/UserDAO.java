package com.demiashkevich.movie.dao;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAO extends AbstractDAO<User>{

    private static final String SEARCH_USER = "SELECT user.email FROM  user WHERE user.email = ?";
    private static final String SELECT_USER = "SELECT user.user_id, user.password, user.first_name, user.last_name, user.email, user.photo, user.admin, user.status FROM user WHERE user.email = ? AND user.password = ? LIMIT 1";
    private static final String INSERT_CREATE_ACCOUNT = "INSERT INTO user(password, first_name, last_name, email, photo, admin, status) VALUES (?,?,?,?,?,?,?)";

    public UserDAO(ProxyConnection connection) {
        super(connection);
    }

    public User findUser(String email, String password){
        User user = null;
        try(PreparedStatement statement = connection.prepareStatement(SELECT_USER)){
            statement.setString(1, email);
            statement.setString(2, password);
            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    user = new User();
                    user.setUserId(resultSet.getInt(1));
                    user.setPassword(resultSet.getString(2));
                    user.setFirstName(resultSet.getString(3));
                    user.setLastName(resultSet.getString(4));
                    user.setEmail(resultSet.getString(5));
                    user.setPhoto(resultSet.getString(6));
                    user.setAdmin(resultSet.getBoolean(7));
                    user.setStatus(resultSet.getBoolean(8));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean isFindUser(String email){
        try(PreparedStatement statement = connection.prepareStatement(SEARCH_USER)){
            statement.setString(1, email);
            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addItem(User user){
        try(PreparedStatement statement = connection.prepareStatement(INSERT_CREATE_ACCOUNT)){
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPhoto());
            statement.setBoolean(6, false);
            statement.setBoolean(7, true);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected List<User> parseResultSet(ResultSet resultSet) {
        return null;
    }

    @Override
    public String getSelectAll() {
        return null;
    }

    @Override
    protected String getSelectLimitByRating() {
        return null;
    }

}
