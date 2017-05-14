package edu.core.java.auction.repository.collection;

import edu.core.java.auction.repository.BuyerRepository;
import edu.core.java.auction.vo.BuyerValueObject;

/**
 * Created by Maxim on 10.05.2017.
 */
public class CollectionBuyerRepository extends CollectionRepository<BuyerValueObject>
                                       implements BuyerRepository{
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
