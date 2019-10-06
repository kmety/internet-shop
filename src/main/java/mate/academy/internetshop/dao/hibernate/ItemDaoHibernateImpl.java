package mate.academy.internetshop.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class ItemDaoHibernateImpl implements ItemDao {
    private static Logger logger = Logger.getLogger(ItemDaoHibernateImpl.class);

    @Override
    public Item add(Item item) {
        Transaction transaction = null;
        Long itemFromDbId;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            itemFromDbId = (Long) session.save(item);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Can't add item", e);
            return null;
        }
        return get(itemFromDbId).get();
    }

    @Override
    public Optional<Item> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Item.class, id));
        } catch (Exception e) {
            logger.error("Can't get item", e);
            return Optional.empty();
        }
    }

    @Override
    public Item update(Item item) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(item);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Can't update item", e);
        }
        return get(item.getId()).get();
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Item item = new Item(id);
            session.delete(item);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Can't delete item", e);
        }
    }

    @Override
    public List<Item> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Item> criteria = builder.createQuery(Item.class);
            criteria.from(Item.class);
            return session.createQuery(criteria).getResultList();
        } catch (Exception e) {
            logger.error("Can't get items list", e);
            return new ArrayList<>();
        }
    }
}
