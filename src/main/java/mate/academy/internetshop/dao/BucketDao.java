package mate.academy.internetshop.dao;

import java.util.List;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;

public interface BucketDao {
    Bucket add(Bucket bucket);

    Bucket get(Long id);

    Bucket addItem(Bucket bucket, Item item);

    Bucket clear(Bucket bucket);

    List<Item> getAllItems(Long bucketId);

    void deleteItem(Long bucketId, Item item);

    Bucket getBucketByUserId(Long userId);
}
