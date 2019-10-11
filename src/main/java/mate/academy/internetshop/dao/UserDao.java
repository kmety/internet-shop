package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.model.User;

public interface UserDao {
    Optional<User> add(User user);

    Optional<User> get(Long id);

    User update(User user);

    void delete(Long id);

    List<User> getAll();

    Optional<User> getUserByLogin(String login);

    Optional<User> getByToken(String token);

    String getSaltByLogin(String login);
}
