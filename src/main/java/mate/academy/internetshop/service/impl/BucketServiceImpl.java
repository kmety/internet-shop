package mate.academy.internetshop.service.impl;

import java.util.List;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.BucketService;

@Service
public class BucketServiceImpl implements BucketService {
    @Inject
    private static BucketDao bucketDao;
    @Inject
    private static ItemDao itemDao;

    @Override
    public Bucket add(Bucket bucket) {
        return bucketDao.add(bucket);
    }

    @Override
    public Bucket get(Long id) {
        return bucketDao.get(id);
    }

    @Override
    public Bucket addItem(Bucket bucket, Item item) {
        return bucketDao.addItem(bucket, item);
    }

    @Override
    public Bucket clear(Bucket bucket) {
        return bucketDao.clear(bucket);
    }

    @Override
    public List<Item> getAllItems(Long bucketId) {
        return bucketDao.getAllItems(bucketId);
    }

    @Override
    public void deleteItem(Long bucketId, Item item) {
        bucketDao.deleteItem(bucketId, item);
    }
}
