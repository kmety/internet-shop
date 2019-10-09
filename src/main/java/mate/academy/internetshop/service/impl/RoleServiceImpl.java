package mate.academy.internetshop.service.impl;

import java.util.Optional;
import java.util.Set;
import mate.academy.internetshop.dao.RoleDao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    @Inject
    private static RoleDao roleDao;

    @Override
    public Set<Role> getRoles(User user) {
        return roleDao.getRoles(user);
    }

    public Optional<Role> getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }
}
