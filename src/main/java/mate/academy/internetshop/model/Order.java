package mate.academy.internetshop.model;

import java.time.LocalDate;
import java.util.List;
import mate.academy.internetshop.idgenerators.OrderIdGenerator;

public class Order {
    private Long id;
    private LocalDate date;
    private List<Item> items;
    private User user;
    private double totalPrice;

    public Order(List<Item> items, User user) {
        id = OrderIdGenerator.generateId();
        date = LocalDate.now();
        this.items = items;
        this.user = user;
        totalPrice = items.stream().mapToDouble(Item::getPrice).sum();
    }

    public Long getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}