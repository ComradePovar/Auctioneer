package edu.core.java.auction.repository.database;

import edu.core.java.auction.AuctionService;
import edu.core.java.auction.repository.ProductRepository;
import edu.core.java.auction.vo.ProductValueObject;
import edu.core.java.auction.vo.SellerValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Maxim on 10.05.2017.
 */
public class DatabaseProductRepository extends DatabaseRepository<ProductValueObject>
                                       implements ProductRepository{
    private String selectAllQuery = "SELECT * FROM products";
    private String selectByIdQuery = "SELECT * FROM products WHERE id = ?";
    private String updateByIdQuery = "UPDATE products SET title = ?, description = ?, ownerId = ? WHERE id = ?";
    private String deleteByIdQuery = "DELETE FROM products WHERE id = ?";
    private String insertQuery = "INSERT INTO products (id, title, description, ownerId) VALUES " +
            " (?, ?, ?, ?)";
    private Logger logger = LoggerFactory.getLogger(DatabaseProductRepository.class);

    @Override
    public boolean contains(Long id) {
        return find(id) != null;
    }

    @Override
    public void add(ProductValueObject object) {
        try{
            Connection connection = AuctionService.getInstance().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setLong(1, object.getId());
            preparedStatement.setString(2, object.getTitle());
            preparedStatement.setString(3, object.getDescription());
            preparedStatement.setLong(4, object.getOwnerId());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    @Override
    public void update(ProductValueObject object) {
        try{
            Connection connection = AuctionService.getInstance().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateByIdQuery);
            preparedStatement.setString(1, object.getTitle());
            preparedStatement.setString(2, object.getDescription());
            preparedStatement.setLong(3, object.getOwnerId());
            preparedStatement.setLong(4, object.getId());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    @Override
    public ProductValueObject find(Long id) {
        try{
            Connection connection = AuctionService.getInstance().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectByIdQuery);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            HashMap<Long, ProductValueObject> set = getValues(resultSet);
            connection.close();
            return set.get(id);
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        try{
            Connection connection = AuctionService.getInstance().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(deleteByIdQuery);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    @Override
    public Collection<ProductValueObject> getAll() {
        try {
            Connection connection = AuctionService.getInstance().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectAllQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            HashMap<Long, ProductValueObject> set = getValues(resultSet);
            connection.close();
            return set.values();
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
        return null;
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
