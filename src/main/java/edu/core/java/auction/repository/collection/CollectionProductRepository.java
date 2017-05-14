package edu.core.java.auction.repository.collection;

import edu.core.java.auction.repository.ProductRepository;
import edu.core.java.auction.vo.ProductValueObject;

/**
 * Created by Maxim on 10.05.2017.
 */
public class CollectionProductRepository extends CollectionRepository<ProductValueObject>
                                         implements ProductRepository{
    protected static Long maxId = 0L;

    @Override
    public Long getMaxId(){
        return maxId;
    }
    @Override
    public void incMaxId(){
        maxId++;
    }
}
