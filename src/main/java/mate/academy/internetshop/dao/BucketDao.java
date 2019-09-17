package mate.academy.internetshop.dao;

import mate.academy.internetshop.model.Bucket;

public interface BucketDao {
    Bucket add(Bucket bucket);

    Bucket get(Long id);

    Bucket update(Bucket bucket);

    void delete(Long id);
}
