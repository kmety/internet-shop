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
        String query = "INSERT INTO " + DB_NAME + ".items (name, price) VALUES (?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setDouble(2, item.getPrice());
            preparedStatement.executeUpdate();
            return item;
        } catch (SQLException e) {
            logger.error("Can't add new item", e);
        }
        return null;
    }

    @Override
    public Item get(Long id) {
        String query = "SELECT * FROM " + DB_NAME + ".items WHERE item_id=" + id + ";";
        try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String name = rs.getString("name");
                Double price = rs.getDouble("price");
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
        String query = "UPDATE " + DB_NAME + ".items SET name = ?, price = ? WHERE (item_id = ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setDouble(2, item.getPrice());
            preparedStatement.setLong(3, item.getId());
            preparedStatement.executeUpdate();
            return item;
        } catch (SQLException e) {
            logger.error("Can't update item", e);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM " + DB_NAME + ".items WHERE (item_id = ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't delete item", e);
        }
    }

    @Override
    public List<Item> getAll() {
        String query = "SELECT * FROM " + DB_NAME + ".items;";
        List<Item> result = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Long id = rs.getLong("item_id");
                String name = rs.getString("name");
                Double price = rs.getDouble("price");
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
