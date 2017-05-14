package edu.core.java.auction.repository.collection;

import edu.core.java.auction.repository.BidRepository;
import edu.core.java.auction.vo.BidValueObject;

/**
 * Created by Maxim on 10.05.2017.
 */
public class CollectionBidRepository extends CollectionRepository<BidValueObject>
                                     implements BidRepository {
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
