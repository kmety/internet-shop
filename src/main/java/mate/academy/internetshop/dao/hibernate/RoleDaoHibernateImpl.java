package mate.academy.internetshop.dao.hibernate;

import java.math.BigInteger;
import java.util.Optional;
import java.util.Set;
import mate.academy.internetshop.dao.RoleDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

@Dao
public class RoleDaoHibernateImpl implements RoleDao {
    @Inject
    private static UserDao userDao;
    private static Logger logger = Logger.getLogger(RoleDaoHibernateImpl.class);

    @Override
    public boolean attachRole(User user) {
        return false;
    }

    @Override
    public Set<Role> getRoles(User user) {
        User userFromDb = userDao.get(user.getId()).get();
        return userFromDb.getRoles();
    }

    @Override
    public Optional<Role> getRoleByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String stringQuery
                    = String.format("SELECT role_id FROM role WHERE role_name = '%s'", name);
            Query query = session.createSQLQuery(stringQuery);
            BigInteger roleId = (BigInteger) query.uniqueResult();
            Role role = Role.of(name);
            role.setId(roleId.longValue());
            return Optional.of(role);
        } catch (Exception e) {
            logger.error("Can't get role by name", e);
            return Optional.empty();
        }
    }
}
