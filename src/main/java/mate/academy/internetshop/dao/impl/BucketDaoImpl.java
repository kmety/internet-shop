package mate.academy.internetshop.dao.impl;

import java.util.NoSuchElementException;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Bucket;

@Dao
public class BucketDaoImpl implements BucketDao {

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
}
