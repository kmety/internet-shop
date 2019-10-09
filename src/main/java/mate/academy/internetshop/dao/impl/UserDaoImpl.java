package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.User;

@Dao
public class UserDaoImpl implements UserDao {
    @Override
    public Optional<User> add(User user) {
        Storage.users.add(user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> get(Long id) {
        return Storage.users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<User> update(User newUser) {
        User user = get(newUser.getId()).get();
        user.setBucket(newUser.getBucket());
        user.setOrders(newUser.getOrders());
        return Optional.of(user);
    }

    @Override
    public void delete(Long id) {
        Storage.users
                .removeIf(user -> user.getId().equals(id));
    }

    @Override
    public List<User> getAll() {
        return Storage.users;
    }

    @Override
    public Optional<User> login(String login, String password)
            throws AuthenticationException {
        Optional<User> userOptional = Storage.users.stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst();
        if (userOptional.isEmpty() || !userOptional.get().getPassword().equals(password)) {
            throw new AuthenticationException("Incorrect login or password");
        }
        return userOptional;
    }

    @Override
    public Optional<User> getByToken(String token) {
        return Storage.users.stream()
                .filter(user -> user.getToken().equals(token))
                .findAny();
    }

    @Override
    public String getSaltByLogin(String login) {
        return "";
    }
}
