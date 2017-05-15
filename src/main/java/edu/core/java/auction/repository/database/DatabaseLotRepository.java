package edu.core.java.auction.repository.database;

import edu.core.java.auction.AuctionService;
import edu.core.java.auction.repository.LotRepository;
import edu.core.java.auction.vo.LotValueObject;
import edu.core.java.auction.vo.ProductValueObject;
import edu.core.java.auction.vo.SellerValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Maxim on 10.05.2017.
 */
public class DatabaseLotRepository extends DatabaseRepository<LotValueObject>
                                   implements LotRepository{
    private String selectAllQuery = "SELECT * FROM lots";
    private String selectByIdQuery = "SELECT * FROM lots WHERE id = ?";
    private String updateByIdQuery = "UPDATE lots SET productId = ?, end_date = ?, current_price = ? WHERE id = ?";
    private String deleteByIdQuery = "DELETE FROM lots WHERE id = ?";
    private String insertQuery = "INSERT INTO lots (id, productID, end_date, current_price) VALUES " +
            " (?, ?, ?, ?)";
    private Logger logger = LoggerFactory.getLogger(DatabaseLotRepository.class);

    @Override
    public boolean contains(Long id) {
        return find(id) != null;
    }

    @Override
    public void add(LotValueObject object) {
        try{
            Connection connection = AuctionService.getInstance().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setLong(1, object.getId());
            preparedStatement.setLong(2, object.getProductId());
            preparedStatement.setDate(3, new Date(object.getEndDate().getTime()));
            preparedStatement.setDouble(4, object.getCurrentPrice());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    @Override
    public void update(LotValueObject object) {
        try{
            Connection connection = AuctionService.getInstance().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateByIdQuery);
            preparedStatement.setLong(1, object.getProductId());
            preparedStatement.setDate(2, new Date(object.getEndDate().getTime()));
            preparedStatement.setDouble(3, object.getCurrentPrice());
            preparedStatement.setLong(4, object.getId());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    @Override
    public LotValueObject find(Long id) {
        try{
            Connection connection = AuctionService.getInstance().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectByIdQuery);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            HashMap<Long, LotValueObject> set = getValues(resultSet);
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
    public Collection<LotValueObject> getAll() {
        try {
            Connection connection = AuctionService.getInstance().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectAllQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            HashMap<Long, LotValueObject> set = getValues(resultSet);
            connection.close();
            return set.values();
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
