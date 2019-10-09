package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;
import org.apache.log4j.Logger;

@Dao
public class OrderDaoJdbcImpl extends AbstractDao<Order> implements OrderDao {
    @Inject
    private static UserDao userDao;
    private static Logger logger = Logger.getLogger(OrderDaoJdbcImpl.class);

    public OrderDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Order add(Order order) {
        String addOrderQuery = "INSERT INTO orders (user_id, date) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(
                addOrderQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, order.getUser().getId());
            Date date = Date.valueOf(order.getDate());
            statement.setDate(2, date);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.error("Can't add new item", e);
            return null;
        }
        String attachItemsQuery = "INSERT INTO orders_items (order_id, item_id) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(attachItemsQuery)) {
            for (Item item : order.getItems()) {
                statement.setLong(1, order.getId());
                statement.setLong(2, item.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error("Can't attach item to order", e);
        }
        return order;
    }

    @Override
    public Order get(Long id) {
        String query = "SELECT * FROM orders WHERE order_id = ?;";
        Order order = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long orderId = resultSet.getLong("order_id");
                Long userId = resultSet.getLong("user_id");
                Date sqlDate = resultSet.getDate("date");
                order = new Order(orderId);
                User user = userDao.get(userId).get();
                LocalDate localDate = sqlDate.toLocalDate();
                List<Item> items = getAllItems(orderId);
                order.setUser(user);
                order.setDate(localDate);
                order.setItems(items);
            }
        } catch (SQLException e) {
            logger.error("Get order by id error", e);
        }
        return order;
    }

    @Override
    public void delete(Long orderId) {
        String query = "DELETE FROM orders WHERE order_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't delete order", e);
        }
    }

    @Override
    public List<Item> getAllItems(Long orderId) {
        List<Item> items = new ArrayList<>();
        String query = "SELECT items.item_id, name, price "
                + "FROM items, orders_items "
                + "WHERE orders_items.item_id=items.item_id "
                + "AND orders_items.order_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderId);
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
            logger.error("Get items from order error", e);
            return null;
        }
        return items;
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT order_id FROM orders WHERE user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long orderId = resultSet.getLong("order_id");
                orders.add(get(orderId));
            }
        } catch (SQLException e) {
            logger.error("getOrdersByUserId error", e);
            return null;
        }
        return orders;
    }
}
