package mate.academy.internetshop.service;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.model.Order;

public interface OrderService {
    Order add(Order order);

    Optional<Order> get(Long id);

    void delete(Long id);

    List<Order> getOrdersByUserId(Long userId);
}
