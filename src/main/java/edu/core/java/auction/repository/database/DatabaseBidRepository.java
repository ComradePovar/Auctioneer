package edu.core.java.auction.repository.database;

import edu.core.java.auction.AuctionService;
import edu.core.java.auction.repository.BidRepository;
import edu.core.java.auction.vo.BidValueObject;
import edu.core.java.auction.vo.LotValueObject;
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
public class DatabaseBidRepository extends DatabaseRepository<BidValueObject>
                                   implements BidRepository{
    private String selectAllQuery = "SELECT * FROM bids";
    private String selectByIdQuery = "SELECT * FROM bids WHERE id = ?";
    private String updateByIdQuery = "UPDATE bids SET lotID = ?, buyerID = ?, amount = ? WHERE id = ?";
    private String deleteByIdQuery = "DELETE FROM bids WHERE id = ?";
    private String insertQuery = "INSERT INTO bids (id, lotID, buyerID, amount) VALUES " +
            " (?, ?, ?, ?)";
    private Logger logger = LoggerFactory.getLogger(DatabaseBidRepository.class);

    @Override
    public boolean contains(Long id) {
        return find(id) != null;
    }

    @Override
    public void add(BidValueObject object) {
        try{
            Connection connection = AuctionService.getInstance().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setLong(1, object.getId());
            preparedStatement.setLong(2, object.getLotId());
            preparedStatement.setLong(3, object.getBuyerId());
            preparedStatement.setDouble(4, object.getAmount());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    @Override
    public void update(BidValueObject object) {
        try{
            Connection connection = AuctionService.getInstance().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateByIdQuery);
            preparedStatement.setLong(1, object.getLotId());
            preparedStatement.setLong(2, object.getBuyerId());
            preparedStatement.setDouble(3, object.getAmount());
            preparedStatement.setLong(4, object.getId());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    @Override
    public BidValueObject find(Long id) {
        try{
            Connection connection = AuctionService.getInstance().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectByIdQuery);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            HashMap<Long, BidValueObject> set = getValues(resultSet);
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
    public Collection<BidValueObject> getAll() {
        try {
            Connection connection = AuctionService.getInstance().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectAllQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            HashMap<Long, BidValueObject> set = getValues(resultSet);
            connection.close();
            return set.values();
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
        return null;
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
