package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.RoleDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.User;
import org.apache.log4j.Logger;

@Dao
public class UserDaoJdbcImpl extends AbstractDao<User> implements UserDao {

    private static Logger logger = Logger.getLogger(UserDaoJdbcImpl.class);
    @Inject
    private static RoleDao roleDao;
    @Inject
    private static BucketDao bucketDao;

    public UserDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public User add(User user) {
        String query = "INSERT INTO users "
                + "(name, surname, login, password, token) VALUES (?, ?, ?, ?, ?);";
        try (PreparedStatement statement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getToken());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
                roleDao.attachRole(user);
            }
        } catch (SQLException e) {
            logger.error("Can't create user", e);
            return null;
        }
        return user;
    }

    @Override
    public User get(Long id) {
        User user = null;
        String query = "SELECT * FROM users WHERE user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("user_id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setLogin(resultSet.getString("login"));
                user.setToken(resultSet.getString("token"));
            }
        } catch (SQLException e) {
            logger.error("Get user by id error", e);
        }
        return user;
    }

    @Override
    public User update(User user) {
        String query = "UPDATE users "
                + "SET name = ?, surname = ?, login = ?, password = ? "
                + "WHERE (user_id = ?);";
        User retrievedUser = get(user.getId());
        if (user.getName() != null) {
            retrievedUser.setName(user.getName());
        }
        if (user.getSurname() != null) {
            retrievedUser.setSurname(user.getSurname());
        }
        if (user.getLogin() != null) {
            retrievedUser.setLogin(user.getLogin());
        }
        if (user.getPassword() != null) {
            retrievedUser.setPassword(user.getPassword());
        }

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, retrievedUser.getName());
            statement.setString(2, retrievedUser.getSurname());
            statement.setString(3, retrievedUser.getLogin());
            statement.setString(4, retrievedUser.getPassword());
            statement.setLong(5, retrievedUser.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't update user", e);
            return null;
        }
        return retrievedUser;
    }

    @Override
    public void delete(Long userId) {
        String query = "DELETE FROM users WHERE user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't delete user", e);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("user_id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setLogin(resultSet.getString("login"));
                user.setToken(resultSet.getString("token"));
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("Get users error", e);
        }
        return users;
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        User user = null;
        String query = "SELECT * FROM users WHERE login = ? AND password = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("user_id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setLogin(resultSet.getString("login"));
                user.setToken(resultSet.getString("token"));
                Bucket bucket = bucketDao.getBucketByUserId(user.getId());
                user.setBucket(bucket);
            } else {
                throw new AuthenticationException("Incorrect login or password");
            }
        } catch (SQLException e) {
            logger.error("Checking login or password error", e);
        }
        return user;
    }

    @Override
    public Optional<User> getByToken(String token) {
        String query = "SELECT * FROM users WHERE token = ?;";
        Optional<User> userOptional = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("user_id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setLogin(resultSet.getString("login"));
                user.setToken(resultSet.getString("token"));
                Bucket bucket = bucketDao.getBucketByUserId(user.getId());
                user.setBucket(bucket);
                userOptional = Optional.of(user);
            }
        } catch (SQLException e) {
            logger.error("Getting by token error", e);
        }
        return userOptional;
    }
}
