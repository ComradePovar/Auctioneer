package edu.core.java.auction.repository.collection;

import edu.core.java.auction.repository.SellerRepository;
import edu.core.java.auction.vo.SellerValueObject;

/**
 * Created by Maxim on 10.05.2017.
 */
public class CollectionSellerRepository extends CollectionRepository<SellerValueObject>
                                        implements SellerRepository{
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
