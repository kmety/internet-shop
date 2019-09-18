package mate.academy.internetshop.db;

import java.util.ArrayList;
import java.util.List;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;

public class Storage {
    public static final List<User> users = new ArrayList<>();
    public static final List<Bucket> buckets = new ArrayList<>();
    public static final List<Item> items = new ArrayList<>();
    public static final List<Order> orders = new ArrayList<>();

    static {
        items.add(new Item("IPhone", 1500.));
        items.add(new Item("MacBook", 3500.));
        items.add(new Item("AppleWatch", 400.));
        items.add(new Item("IPad", 1500.));
        items.add(new Item("AirPods", 200.));
    }
}
