package mate.academy.internetshop.dao;

import java.util.Optional;
import java.util.Set;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;

public interface RoleDao {
    void attachRole(User user);

    Set<Role> getRoles(User user);

    Optional<Role> getRoleByName(String name);
}
