package mate.academy.internetshop.dao;

import java.util.Set;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;

public interface RoleDao {
    boolean attachRole(User user);

    boolean attachRole(User user, Role role);

    boolean detachRole(User user, Role role);

    Set<Role> getRoles(User user);
}
