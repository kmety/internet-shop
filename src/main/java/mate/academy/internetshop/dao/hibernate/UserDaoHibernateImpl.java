package mate.academy.internetshop.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class UserDaoHibernateImpl implements UserDao {
    private static Logger logger = Logger.getLogger(UserDaoHibernateImpl.class);

    @Override
    public Optional<User> add(User user) {
        Transaction transaction = null;
        Session session = null;
        Long userFromDbId;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            userFromDbId = (Long) session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Can't add user", e);
            return Optional.empty();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        user.setId(userFromDbId);
        return Optional.of(user);
    }

    @Override
    public Optional<User> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            logger.error("Can't get user", e);
            return Optional.empty();
        }
    }

    @Override
    public User update(User user) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Can't update user", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            User user = new User();
            user.setId(id);
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Can't delete user", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("from User");
            users = query.list();
        } catch (Exception e) {
            logger.error("Can't get users list", e);
            return new ArrayList<>();
        }
        return users;
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("from User where login=:login");
            query.setParameter("login", login);
            User user = (User) query.uniqueResult();
            return Optional.ofNullable(user);
        }
    }

    @Override
    public Optional<User> getByToken(String token) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("from User where token=:token");
            query.setParameter("token", token);
            User user = (User) query.uniqueResult();
            return Optional.ofNullable(user);
        }
    }

    @Override
    public String getSaltByLogin(String login) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("select salt from User where login=:login");
            query.setParameter("login", login);
            return (String) query.uniqueResult();
        }
    }
}
