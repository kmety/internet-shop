package mate.academy.internetshop.model;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private Long id;
    private  LocalDate date;
    private List<Item> items;
    private User user;

    public Order(Long id) {
        this.id = id;
        date = LocalDate.now();
    }

    public Order(List<Item> items, User user) {
        date = LocalDate.now();
        this.items = items;
        this.user = user;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
