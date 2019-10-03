package mate.academy.internetshop.dao;

import java.util.List;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;

public interface OrderDao {
    Order add(Order order);

    Order get(Long id);

    void delete(Long id);

    List<Item> getAllItems(Long orderId);

    List<Order> getOrdersByUserId(Long userId);
}
