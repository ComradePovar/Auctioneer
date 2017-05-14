package edu.core.java.auction.repository.collection;

import edu.core.java.auction.repository.LotRepository;
import edu.core.java.auction.vo.LotValueObject;

/**
 * Created by Maxim on 10.05.2017.
 */
public class CollectionLotRepository extends CollectionRepository<LotValueObject>
                                     implements LotRepository{
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
