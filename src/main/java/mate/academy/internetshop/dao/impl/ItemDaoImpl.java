package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;

@Dao
public class ItemDaoImpl implements ItemDao {
    @Override
    public Item add(Item item) {
        Storage.items.add(item);
        return item;
    }

    @Override
    public Optional<Item> get(Long id) {
        return Storage.items.stream()
                .filter(element -> element.getId().equals(id))
                .findFirst();
    }

    @Override
    public Item update(Item newItem) {
        Item item = get(newItem.getId()).get();
        item.setName(newItem.getName());
        item.setPrice(newItem.getPrice());
        return item;
    }

    @Override
    public void delete(Long id) {
        Storage.items
                .removeIf(item -> item.getId().equals(id));
    }

    @Override
    public List<Item> getAll() {
        return Storage.items;
    }
}
