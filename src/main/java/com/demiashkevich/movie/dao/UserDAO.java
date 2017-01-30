package com.demiashkevich.movie.dao;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.User;
import com.demiashkevich.movie.exception.DAOException;
import com.demiashkevich.movie.type.ClientRank;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO<User>{

    private static final int START_EXPERIENCE = 0;
    private static final boolean START_STATUS = true;
    private static final boolean START_ADMINISTRATOR = false;
    private static final String START_RANK = ClientRank.BAFTA.getNameRank();

    private static final String SELECT_USER = "SELECT user.user_id, user.password, user.first_name, user.last_name, user.email, user.photo, user.admin, user.status, user.experience, user.rank FROM user WHERE user.email = ? LIMIT 1";
    private static final String INSERT_CREATE_ACCOUNT = "INSERT INTO user(password, first_name, last_name, email, photo, admin, status, experience, rank) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String DELETE_USER = "DELETE FROM user WHERE user.user_id = ?";
    private static final String SELECT_ALL_USER = "SELECT user.user_id, user.first_name, user.last_name, user.status FROM user WHERE user.admin = 0";
    private static final String UPDATE_USER_STATUS = "UPDATE user SET user.status = ? WHERE user.user_id = ?";
    private static final String UPDATE_USER_EXPERIENCE = "UPDATE user SET user.experience = user.experience + ? WHERE user.user_id = ?";
    private static final String UPDATE_USER_RANK = "UPDATE user SET user.rank = ? WHERE user.user_id = ?";

    public UserDAO(ProxyConnection connection) {
        super(connection);
    }

    public boolean updateExperience(final int USER_ID, final int EXPERIENCE) throws DAOException {
        try(PreparedStatement statement = connection.prepareStatement(UPDATE_USER_EXPERIENCE)){
            statement.setInt(1, EXPERIENCE);
            statement.setInt(2, USER_ID);
            statement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            throw new DAOException(exception);
        }
    }

    public boolean updateRankUser(final int USER_ID, final String RANK_NAME) throws DAOException {
        try(PreparedStatement statement = connection.prepareStatement(UPDATE_USER_RANK)){
            statement.setString(1, RANK_NAME);
            statement.setInt(2, USER_ID);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DAOException(exception);
        }
        return true;
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
                    user.setExperience(resultSet.getInt(9));
                    user.setRank(resultSet.getString(10));
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
            statement.setBoolean(6, START_ADMINISTRATOR);
            statement.setBoolean(7, START_STATUS);
            statement.setInt(8, START_EXPERIENCE);
            statement.setString(9, START_RANK);
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
