package mate.academy.internetshop.dao.hibernate;

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
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Role where roleName=:role");
            Role.RoleName roleName = Role.of(name).getRoleName();
            query.setParameter("role", roleName);
            Role role = (Role) query.uniqueResult();
            session.close();
            return Optional.ofNullable(role);
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            logger.error("Can't get role by name", e);
            return Optional.empty();
        }
    }
}
