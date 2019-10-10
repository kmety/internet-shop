package mate.academy.internetshop.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class BucketDaoHibernateImpl implements BucketDao {
    @Inject
    private static UserDao userDao;
    @Inject
    private static ItemDao itemDao;
    private static Logger logger = Logger.getLogger(BucketDaoHibernateImpl.class);

    @Override
    public Bucket add(Bucket bucket) {
        Transaction transaction = null;
        Session session = null;
        Long bucketFromDbId = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            bucketFromDbId = (Long) session.save(bucket);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Can't add bucket", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        bucket.setId(bucketFromDbId);
        return bucket;
    }

    @Override
    public Optional<Bucket> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Bucket.class, id));
        } catch (Exception e) {
            logger.error("Can't get bucket", e);
            return Optional.empty();
        }
    }

    @Override
    public Bucket addItem(Bucket bucket, Item item) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            bucket = get(bucket.getId()).orElseThrow(() -> new Exception("Bucket is absent"));
            item = itemDao.get(item.getId()).orElseThrow(() -> new Exception("Item is absent"));
            bucket.getItems().add(item);
            session.update(bucket);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Can't add item into bucket", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return bucket;
    }

    @Override
    public Bucket clear(Bucket bucket) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            bucket = get(bucket.getId()).orElseThrow(() -> new Exception("Bucket is absent"));
            bucket.setItems(new ArrayList<>());
            session.update(bucket);
        } catch (Exception e) {
            logger.error("Can't clear bucket", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return bucket;
    }

    @Override
    public List<Item> getAllItems(Long bucketId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Bucket bucket = get(bucketId).get();
            return bucket.getItems();
        } catch (Exception e) {
            logger.error("Can't get all items from bucket", e);
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteItem(Long bucketId, Item item) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Bucket bucket = get(bucketId).orElseThrow(() -> new Exception("Bucket is absent"));
            bucket.getItems().remove(item);
            session.update(bucket);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Can't delete item", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Bucket getBucketByUserId(Long userId) {
        User user = userDao.get(userId).get();
        return user.getBucket();
    }
}
