package mate.academy.internetshop.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class OrderDaoHibernateImpl implements OrderDao {
    private static final Logger logger = Logger.getLogger(OrderDaoHibernateImpl.class);
    @Inject
    private static UserDao userDao;

    @Override
    public Order add(Order order) {
        Session session = null;
        Transaction transaction = null;
        Long orderId = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            orderId = (Long) session.save(order);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't add new order", e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        order.setId(orderId);
        return order;
    }

    @Override
    public Optional<Order> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Order.class, id));
        } catch (Exception e) {
            logger.error("Can't get order", e);
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        Session session = null;
        Transaction transaction = null;
        try {
            Order order = get(id).orElseThrow(
                    () -> new Exception("Can't get order by id"));
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(order);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Can't delete order", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Item> getAllItems(Long orderId) {
        try {
            Order order = get(orderId).orElseThrow(
                    () -> new Exception("Can't get items from order"));
            return order.getItems();
        } catch (Exception e) {
            logger.error("Can't get items from order", e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Order WHERE user.id=:userId");
            query.setParameter("userId", userId);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Can't get orders by userId", e);
            return new ArrayList<>();
        }
    }
}
