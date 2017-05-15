package edu.core.java.auction.repository.database;

import edu.core.java.auction.AuctionService;
import edu.core.java.auction.domain.Buyer;
import edu.core.java.auction.repository.BuyerRepository;
import edu.core.java.auction.vo.BuyerValueObject;
import edu.core.java.auction.vo.LotValueObject;
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
public class DatabaseBuyerRepository extends DatabaseRepository<BuyerValueObject>
                                     implements BuyerRepository{
    private String selectAllQuery = "SELECT * FROM buyers";
    private String selectByIdQuery = "SELECT * FROM buyers WHERE id = ?";
    private String updateByIdQuery = "UPDATE buyers SET name = ?, account_balance = ? WHERE id = ?";
    private String deleteByIdQuery = "DELETE FROM buyers WHERE id = ?";
    private String insertQuery = "INSERT INTO buyers (id, name, account_balance) VALUES " +
            " (?, ?, ?)";
    private Logger logger = LoggerFactory.getLogger(DatabaseBuyerRepository.class);

    @Override
    public boolean contains(Long id) {
        return find(id) != null;
    }

    @Override
    public void add(BuyerValueObject object) {
        try{
            Connection connection = AuctionService.getInstance().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setLong(1, object.getId());
            preparedStatement.setString(2, object.getName());
            preparedStatement.setDouble(3, object.getAccountBalance());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    @Override
    public void update(BuyerValueObject object) {
        try{
            Connection connection = AuctionService.getInstance().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateByIdQuery);
            preparedStatement.setString(1, object.getName());
            preparedStatement.setDouble(2, object.getAccountBalance());
            preparedStatement.setLong(3, object.getId());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    @Override
    public BuyerValueObject find(Long id) {
        try{
            Connection connection = AuctionService.getInstance().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectByIdQuery);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            HashMap<Long, BuyerValueObject> set = getValues(resultSet);
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
    public Collection<BuyerValueObject> getAll() {
        try {
            Connection connection = AuctionService.getInstance().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectAllQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            HashMap<Long, BuyerValueObject> set = getValues(resultSet);
            connection.close();
            return set.values();
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
        return null;
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
