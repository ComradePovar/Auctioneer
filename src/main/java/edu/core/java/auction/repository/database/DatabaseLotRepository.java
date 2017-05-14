package edu.core.java.auction.repository.database;

import edu.core.java.auction.repository.LotRepository;
import edu.core.java.auction.vo.LotValueObject;
import edu.core.java.auction.vo.ProductValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Maxim on 10.05.2017.
 */
public class DatabaseLotRepository extends DatabaseRepository<LotValueObject>
                                   implements LotRepository{
    protected String tableName = "lots";
    private Logger logger = LoggerFactory.getLogger(DatabaseLotRepository.class);

    @Override
    public boolean contains(Long id) {
        return find(id) != null;
    }

    @Override
    public void add(LotValueObject object) {
        try{
            String values = object.id + ", " + object.productId + ", '" + object.endDate + "', " + object.currentPrice;
            String query = getInsertQuery(tableName + "(id, productID, end_date, current_price)", values);
            modify(query);
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    @Override
    public void update(LotValueObject object) {
        try{
            String values = "productID = " + object.productId +
                    ", end_date = " + object.endDate +
                    ", current_price = " + object.currentPrice;
            String condition = "id = " + object.id;
            String query = getUpdateQuery(tableName, values, condition);
            modify(query);
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    @Override
    public LotValueObject find(Long id) {
        try{
            LotValueObject result;
            String condition = "id = " + id;
            String query = getSelectQuery(tableName, condition);
            HashMap<Long, LotValueObject> results = select(query);
            return results.get(id);
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        try{
            String condition = "id = " + id;
            String query = getDeleteQuery(tableName, condition);
            modify(query);
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    @Override
    public Collection<LotValueObject> getAll() {
        try {
            String query = getSelectAllQuery(tableName);
            HashMap<Long, LotValueObject> results = select(query);
            return results.values();
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
        return null;
    }

    @Override
    protected HashMap<Long, LotValueObject> getValues(ResultSet set) throws SQLException {
        HashMap<Long, LotValueObject> results = new HashMap<>();
        while (set.next()){
            LotValueObject vo = new LotValueObject();
            vo.id = set.getLong("id");
            vo.productId = set.getLong("productID");
            vo.endDate = set.getDate("end_date");
            vo.currentPrice = set.getLong("current_price");
            results.put(vo.id, vo);
        }
        return results;
    }
}
