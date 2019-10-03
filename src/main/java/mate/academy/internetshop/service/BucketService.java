package mate.academy.internetshop.service;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;

public interface BucketService {
    Bucket add(Bucket bucket);

    Optional<Bucket> get(Long id);

    Bucket addItem(Bucket bucket, Item item);

    Bucket clear(Bucket bucket);

    List<Item> getAllItems(Long bucketId);

    void deleteItem(Long bucketId, Item item);
}
