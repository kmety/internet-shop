package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
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
    public Bucket get(Long id) {
        return Storage.buckets.stream()
                .filter(bucket -> bucket.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("Can't find bucket with id " + id));
    }

    @Override
    public Bucket update(Bucket newBucket) {
        Bucket bucket = get(newBucket.getId());
        bucket.setItems(newBucket.getItems());
        bucket.setUser(newBucket.getUser());
        return bucket;
    }

    @Override
    public void delete(Long id) {
        Storage.buckets
                .removeIf(bucket -> bucket.getId().equals(id));
    }

    @Override
    public Bucket addItem(Bucket bucket, Item item) {
        Item retrievedItem = itemDao.get(item.getId());
        bucket.getItems().add(retrievedItem);
        return get(bucket.getId());
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
        Bucket bucket = get(bucketId);
        bucket.getItems().remove(item);
    }

    @Override
    public Bucket getBucketByUserId(Long userId) {
        return null;
    }
}
