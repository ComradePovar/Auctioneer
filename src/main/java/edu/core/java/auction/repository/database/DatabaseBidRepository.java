package edu.core.java.auction.repository.database;

import edu.core.java.auction.repository.BidRepository;
import edu.core.java.auction.vo.BidValueObject;
import edu.core.java.auction.vo.LotValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Maxim on 10.05.2017.
 */
public class DatabaseBidRepository extends DatabaseRepository<BidValueObject>
                                   implements BidRepository{
    protected static Long maxId = 0L;
    protected String tableName = "bids";
    private Logger logger = LoggerFactory.getLogger(DatabaseBidRepository.class);

    @Override
    public boolean contains(Long id) {
        return find(id) != null;
    }

    @Override
    public void add(BidValueObject object) {
        try{
            String values = object.lotId + ", " + object.buyerId + ", " + object.amount;
            String query = getInsertQuery(tableName + "(lotID, buyerID, amount)", values);
            modify(query);
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    @Override
    public void update(BidValueObject object) {
        try{
            String values = "lotID = " + object.lotId +
                    ", buyerID = " + object.buyerId +
                    ", amount = " + object.amount;
            String condition = object.id.toString();
            String query = getUpdateQuery(tableName, values, condition);
            modify(query);
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    @Override
    public BidValueObject find(Long id) {
        try{
            BidValueObject result;
            String condition = "id = " + id.toString();
            String query = getSelectQuery(tableName, condition);
            HashMap<Long, BidValueObject> results = select(query);
            return results.get(id);
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        try{
            String condition = "id = " + id.toString();
            String query = getDeleteQuery(tableName, condition);
            modify(query);
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    @Override
    public Collection<BidValueObject> getAll() {
        try {
            String query = getSelectAllQuery(tableName);
            HashMap<Long, BidValueObject> results = select(query);
            return results.values();
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
        return null;
    }

    @Override
    public Long getMaxId(){
        return maxId;
    }
    @Override
    public void incMaxId(){
        maxId++;
    }

    @Override
    protected HashMap<Long, BidValueObject> getValues(ResultSet set) throws SQLException {
        HashMap<Long, BidValueObject> results = new HashMap<>();
        while (set.next()){
            BidValueObject vo = new BidValueObject();
            vo.id = set.getLong("id");
            vo.lotId = set.getLong("lotID");
            vo.buyerId = set.getLong("buyerID");
            vo.amount = set.getDouble("amount");
            results.put(vo.id, vo);
        }
        return results;
    }
}
