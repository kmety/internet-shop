package mate.academy.internetshop.service;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;

public interface UserService {
    User add(User user);

    User get(Long id);

    User update(User user);

    void delete(Long id);

    List<Order> getOrders(User user);

    List<User> getAll();

    User login(String login, String password) throws AuthenticationException;

    Optional<User> getByToken(String token);

    String getSaltByLogin(String login);
}
