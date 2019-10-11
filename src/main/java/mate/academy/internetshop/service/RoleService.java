package mate.academy.internetshop.service;

import java.util.Optional;
import java.util.Set;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;

public interface RoleService {

    Set<Role> getRoles(User user);

    Optional<Role> getRoleByName(String name);
}
