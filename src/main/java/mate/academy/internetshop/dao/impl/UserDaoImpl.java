package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.User;

@Dao
public class UserDaoImpl implements UserDao {
    @Override
    public User add(User user) {
        Storage.users.add(user);
        return user;
    }

    @Override
    public User get(Long id) {
        return Storage.users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find user with id " + id));
    }

    @Override
    public User update(User newUser) {
        User user = get(newUser.getId());
        user.setBucket(newUser.getBucket());
        user.setOrders(newUser.getOrders());
        return user;
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
    public User login(String login, String password)
            throws AuthenticationException {
        Optional<User> userOptional = Storage.users.stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst();
        if (userOptional.isEmpty() || !userOptional.get().getPassword().equals(password)) {
            throw new AuthenticationException("Incorrect login or password");
        }
        return userOptional.get();
    }

    @Override
    public Optional<User> getByToken(String token) {
        return Storage.users.stream()
                .filter(user -> user.getToken().equals(token))
                .findAny();
    }
}
