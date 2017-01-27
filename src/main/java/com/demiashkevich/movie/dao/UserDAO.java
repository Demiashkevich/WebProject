package com.demiashkevich.movie.dao;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.User;
import com.demiashkevich.movie.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO<User>{

    private static final String SELECT_USER = "SELECT user.user_id, user.password, user.first_name, user.last_name, user.email, user.photo, user.admin, user.status FROM user WHERE user.email = ? LIMIT 1";
    private static final String INSERT_CREATE_ACCOUNT = "INSERT INTO user(password, first_name, last_name, email, photo, admin, status) VALUES (?,?,?,?,?,?,?)";
    private static final String DELETE_USER = "DELETE FROM user WHERE user.user_id = ?";
    private static final String SELECT_ALL_USER = "SELECT user.user_id, user.first_name, user.last_name, user.status FROM user WHERE user.admin = 0";
    private static final String UPDATE_USER_STATUS = "UPDATE user SET user.status = ? WHERE user.user_id = ?";

    public UserDAO(ProxyConnection connection) {
        super(connection);
    }

    public User findUser(String email) throws DAOException {
        try(PreparedStatement statement = connection.prepareStatement(SELECT_USER)){
            statement.setString(1, email);
            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    User user = new User();
                    user.setUserId(resultSet.getInt(1));
                    user.setPassword(resultSet.getString(2));
                    user.setFirstName(resultSet.getString(3));
                    user.setLastName(resultSet.getString(4));
                    user.setEmail(resultSet.getString(5));
                    user.setPhoto(resultSet.getString(6));
                    user.setAdmin(resultSet.getBoolean(7));
                    user.setStatus(resultSet.getBoolean(8));
                    return user;
                }
            }
        } catch (SQLException exception) {
            throw new DAOException(exception);
        }
        return null;
    }

    public boolean updateStatusUser(final int USER_ID, final boolean STATUS) throws DAOException {
        try(PreparedStatement statement = connection.prepareStatement(UPDATE_USER_STATUS)){
            statement.setBoolean(1, STATUS);
            statement.setInt(2, USER_ID);
            statement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            throw new DAOException(exception);
        }
    }

    @Override
    public boolean addItem(User user) throws DAOException {
        try(PreparedStatement statement = connection.prepareStatement(INSERT_CREATE_ACCOUNT)){
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPhoto());
            statement.setBoolean(6, false);
            statement.setBoolean(7, true);
            statement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            throw new DAOException(exception);
        }
    }

    @Override
    public List<User> findItemsByMovieId(final long MOVIE_ID, final boolean OCCUPANCY) {
        return null;
    }

    @Override
    public List<User> findItemsByActorId(final long ACTOR_ID, final boolean OCCUPANCY) {
        return null;
    }

    @Override
    public List<User> findAllItems() throws DAOException {
        return this.findAll(SELECT_ALL_USER);
    }

    @Override
    public List<User> findItems(final int FROM, final int COUNT) {
        return null;
    }

    @Override
    public int findCountRecords() {
        return 0;
    }

    @Override
    public boolean deleteItem(final long USER_ID) throws DAOException {
        return delete(DELETE_USER, USER_ID);
    }

    @Override
     protected List<User> parseResultSetFull(ResultSet resultSet) {
        return null;
    }

    @Override
    protected List<User> parseResultSetLazy(ResultSet resultSet) throws DAOException {
        List<User> users = new ArrayList<>();
        try {
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt(1));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setStatus(resultSet.getBoolean(4));
                users.add(user);
            }
        } catch (SQLException exception) {
            throw new DAOException(exception);
        }
        return users;
    }

}
