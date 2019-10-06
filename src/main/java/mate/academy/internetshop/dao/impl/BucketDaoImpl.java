package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;

@Dao
public class BucketDaoImpl implements BucketDao {
    @Inject
    private static ItemDao itemDao;

    @Override
    public Bucket add(Bucket bucket) {
        Storage.buckets.add(bucket);
        return bucket;
    }

    @Override
    public Optional<Bucket> get(Long id) {
        return Storage.buckets.stream()
                .filter(bucket -> bucket.getId().equals(id))
                .findFirst();
    }

    @Override
    public Bucket addItem(Bucket bucket, Item item) {
        Item retrievedItem = itemDao.get(item.getId()).get();
        bucket.getItems().add(retrievedItem);
        return get(bucket.getId()).get();
    }

    @Override
    public Bucket clear(Bucket bucket) {
        bucket.getItems().clear();
        return bucket;
    }

    @Override
    public List<Item> getAllItems(Long bucketId) {
        return null;
    }

    @Override
    public void deleteItem(Long bucketId, Item item) {
        Bucket bucket = get(bucketId).get();
        bucket.getItems().remove(item);
    }

    @Override
    public Bucket getBucketByUserId(Long userId) {
        return null;
    }
}
