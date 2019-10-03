package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;
import org.apache.log4j.Logger;

@Dao
public class ItemDaoJdbcImpl extends AbstractDao<Item> implements ItemDao {
    private static Logger logger = Logger.getLogger(ItemDaoJdbcImpl.class);

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item add(Item item) {
        String query = "INSERT INTO items (name, price) VALUES (?, ?);";
        try (PreparedStatement statement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.setDouble(2, item.getPrice());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating item failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.error("Can't add new item", e);
            return null;
        }
        return item;
    }

    @Override
    public Item get(Long id) {
        String query = "SELECT * FROM items WHERE item_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                Item item = new Item(id);
                item.setName(name);
                item.setPrice(price);
                return item;
            }
        } catch (SQLException e) {
            logger.error("Can't get item by id " + id, e);
        }
        return null;
    }

    @Override
    public Item update(Item item) {
        String query = "UPDATE items SET name = ?, price = ? WHERE (item_id = ?);";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, item.getName());
            statement.setDouble(2, item.getPrice());
            statement.setLong(3, item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't update item", e);
            return null;
        }
        return item;
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM items WHERE (item_id = ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't delete item", e);
        }
    }

    @Override
    public List<Item> getAll() {
        String query = "SELECT * FROM items;";
        List<Item> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("item_id");
                String name = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                Item item = new Item();
                item.setId(id);
                item.setName(name);
                item.setPrice(price);
                result.add(item);
            }
        } catch (SQLException e) {
            logger.error("Can't create item list", e);
        }
        return result;
    }
}
