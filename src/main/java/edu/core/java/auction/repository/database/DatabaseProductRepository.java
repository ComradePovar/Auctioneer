package edu.core.java.auction.repository.database;

import edu.core.java.auction.repository.ProductRepository;
import edu.core.java.auction.vo.ProductValueObject;
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
public class DatabaseProductRepository extends DatabaseRepository<ProductValueObject>
                                       implements ProductRepository{
    protected static Long maxId = 0L;
    protected String tableName = "products";
    private Logger logger = LoggerFactory.getLogger(DatabaseProductRepository.class);

    @Override
    public boolean contains(Long id) {
        return find(id) != null;
    }

    @Override
    public void add(ProductValueObject object) {
        try{
            String values = "'" + object.title + "', '" + object.description + "', " + object.ownerId;
            String query = getInsertQuery(tableName + "(title, description, ownerID)", values);
            modify(query);
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    @Override
    public void update(ProductValueObject object) {
        try{
            String values = "title = '" + object.title +
                    "', description = " + object.description +
                    ", ownerID = " + object.ownerId;
            String condition = object.id.toString();
            String query = getUpdateQuery(tableName, values, condition);
            modify(query);
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    @Override
    public ProductValueObject find(Long id) {
        try{
            ProductValueObject result;
            String condition = "id = " + id.toString();
            String query = getSelectQuery(tableName, condition);
            HashMap<Long, ProductValueObject> results = select(query);
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
    public Collection<ProductValueObject> getAll() {
        try {
            String query = getSelectAllQuery(tableName);
            HashMap<Long, ProductValueObject> results = select(query);
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
    protected HashMap<Long, ProductValueObject> getValues(ResultSet set) throws SQLException {
        HashMap<Long, ProductValueObject> results = new HashMap<>();
        while (set.next()){
            ProductValueObject vo = new ProductValueObject();
            vo.id = set.getLong("id");
            vo.title = set.getString("title");
            vo.description = set.getString("description");
            vo.ownerId = set.getLong("ownerID");
            results.put(vo.id, vo);
        }
        return results;
    }
}
