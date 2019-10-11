package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.User;
import org.apache.log4j.Logger;

@Dao
public class BucketDaoJdbcImpl extends AbstractDao<Bucket> implements BucketDao {
    @Inject
    private static UserDao userDao;
    private static Logger logger = Logger.getLogger(BucketDaoJdbcImpl.class);

    public BucketDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Bucket add(Bucket bucket) {
        Long userId = bucket.getUser().getId();
        String query = "INSERT INTO bucket (user_id) VALUES (?);";
        try (PreparedStatement statement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, userId);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    bucket.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating bucket failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.error("Can't create bucket", e);
            return null;
        }
        return bucket;
    }

    @Override
    public Optional<Bucket> get(Long id) {
        String query = "SELECT * FROM bucket WHERE bucket_id = ?;";
        Optional<Bucket> bucketOptional = Optional.empty();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long bucketId = resultSet.getLong("bucket_id");
                Long userId = resultSet.getLong("user_id");
                Bucket bucket = new Bucket(bucketId);
                User user = userDao.get(userId).get();
                List<Item> items = getAllItems(bucketId);
                bucket.setUser(user);
                bucket.setItems(items);
                bucketOptional = Optional.of(bucket);
            }
        } catch (SQLException e) {
            logger.error("Get bucket by id error", e);
        }
        return bucketOptional;
    }

    @Override
    public Bucket addItem(Bucket bucket, Item item) {
        String query = "INSERT INTO bucket_item (bucket_id, item_id) Values (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, bucket.getId());
            statement.setLong(2, item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't add item to bucket", e);
            return null;
        }
        return get(bucket.getId()).get();
    }

    @Override
    public Bucket clear(Bucket bucket) {
        String query = "DELETE FROM bucket_item WHERE bucket_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, bucket.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't clear the bucket", e);
            return null;
        }
        return get(bucket.getId()).get();
    }

    @Override
    public List<Item> getAllItems(Long bucketId) {
        List<Item> items = new ArrayList<>();
        String query = "SELECT items.item_id, name, price "
                + "FROM items INNER JOIN bucket_item "
                + "ON bucket_item.item_id=items.item_id "
                + "WHERE bucket_item.bucket_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, bucketId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long itemId = resultSet.getLong("items.item_id");
                String name = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                Item item = new Item(name, price);
                item.setId(itemId);
                items.add(item);
            }
        } catch (SQLException e) {
            logger.error("Get items from bucket error", e);
            return null;
        }
        return items;
    }

    @Override
    public void deleteItem(Long bucketId, Item item) {
        String query = "DELETE FROM bucket_item WHERE bucket_id = ? AND item_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, bucketId);
            statement.setLong(2, item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't delete item from bucket", e);
        }
    }

    @Override
    public Bucket getBucketByUserId(Long userId) {
        String query = "SELECT bucket_id FROM bucket WHERE user_id = ?;";
        Long bucketId = null;
        Bucket bucket = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                bucketId = resultSet.getLong("bucket_id");
            }
        } catch (SQLException e) {
            logger.error("Get bucket by id error", e);
        }
        if (bucketId != null) {
            bucket = get(bucketId).get();
        }
        return bucket;
    }
}
