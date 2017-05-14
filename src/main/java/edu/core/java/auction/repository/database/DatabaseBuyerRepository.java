package edu.core.java.auction.repository.database;

import edu.core.java.auction.domain.Buyer;
import edu.core.java.auction.repository.BuyerRepository;
import edu.core.java.auction.vo.BuyerValueObject;
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
public class DatabaseBuyerRepository extends DatabaseRepository<BuyerValueObject>
                                     implements BuyerRepository{
    protected static Long maxId = 0L;
    protected String tableName = "buyers";
    private Logger logger = LoggerFactory.getLogger(DatabaseBuyerRepository.class);

    @Override
    public boolean contains(Long id) {
        return find(id) != null;
    }

    @Override
    public void add(BuyerValueObject object) {
        try{
            String values = "'" + object.name + "', " + object.accountBalance;
            String query = getInsertQuery(tableName + "(name, account_balance)", values);
            modify(query);
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    @Override
    public void update(BuyerValueObject object) {
        try{
            String values = "name = '" + object.name +
                    "', account_balance = " + object.accountBalance;
            String condition = object.id.toString();
            String query = getUpdateQuery(tableName, values, condition);
            modify(query);
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    @Override
    public BuyerValueObject find(Long id) {
        try{
            BuyerValueObject result;
            String condition = "id = " + id.toString();
            String query = getSelectQuery(tableName, condition);
            HashMap<Long, BuyerValueObject> results = select(query);
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
    public Collection<BuyerValueObject> getAll() {
        try {
            String query = getSelectAllQuery(tableName);
            HashMap<Long, BuyerValueObject> results = select(query);
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
    protected HashMap<Long, BuyerValueObject> getValues(ResultSet set) throws SQLException {
        HashMap<Long, BuyerValueObject> results = new HashMap<>();
        while(set.next()){
            BuyerValueObject vo = new BuyerValueObject();
            vo.id = set.getLong("id");
            vo.name = set.getString("name");
            vo.accountBalance = set.getDouble("account_balance");
            results.put(vo.id, vo);
        }
        return results;
    }
}
