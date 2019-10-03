package mate.academy.internetshop.dao;

import java.util.Set;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;

public interface RoleDao {
    boolean attachRole(User user);

    Set<Role> getRoles(User user);
}
