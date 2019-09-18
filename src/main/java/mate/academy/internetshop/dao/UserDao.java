package mate.academy.internetshop.dao;

import java.util.List;
import mate.academy.internetshop.model.User;

public interface UserDao {
    User add(User user);

    User get(Long id);

    User update(User user);

    void delete(Long id);

    List<User> getAll();
}
