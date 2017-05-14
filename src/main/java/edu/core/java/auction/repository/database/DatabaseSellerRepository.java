package edu.core.java.auction.repository.database;

import edu.core.java.auction.repository.SellerRepository;
import edu.core.java.auction.vo.SellerValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Maxim on 10.05.2017.
 */
public class DatabaseSellerRepository extends DatabaseRepository<SellerValueObject>
                                      implements SellerRepository{
    protected static Long maxId = 0L;
    protected String tableName = "sellers";
    private Logger logger = LoggerFactory.getLogger(DatabaseSellerRepository.class);

    @Override
    public boolean contains(Long id) {
        return find(id) != null;
    }

    @Override
    public void add(SellerValueObject object) {
        try{
            String values = "'" + object.name + "', '" + object.accountBalance + "', '" + object.commissionPercentage + "'";
            String query = getInsertQuery(tableName + "(name, account_balance, commission_percentage)", values);
            modify(query);
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    @Override
    public void update(SellerValueObject object) {
        try{
            String values = "name = '" + object.name +
                            "', account_balance = " + object.accountBalance +
                            ", commission_percentage = " + object.commissionPercentage;
            String condition = object.id.toString();
            String query = getUpdateQuery(tableName, values, condition);
            modify(query);
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    @Override
    public SellerValueObject find(Long id) {
        try{
            SellerValueObject result;
            String condition = "id = " + id.toString();
            String query = getSelectQuery(tableName, condition);
            HashMap<Long, SellerValueObject> set = select(query);
            return set.get(id);
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
    public Collection<SellerValueObject> getAll() {
        try {
            String query = getSelectAllQuery(tableName);
            HashMap<Long, SellerValueObject> set = select(query);
            return set.values();
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
    protected HashMap<Long, SellerValueObject> getValues(ResultSet set) throws SQLException{
        HashMap<Long, SellerValueObject> values = new HashMap<>();
        while (set.next()){
            SellerValueObject vo = new SellerValueObject();
            vo.id = set.getLong("id");
            vo.name = set.getString("name");
            vo.accountBalance = set.getDouble("account_balance");
            vo.commissionPercentage = set.getDouble("commission_percentage");
            values.put(vo.id, vo);
        }
        return values;
    }
}
