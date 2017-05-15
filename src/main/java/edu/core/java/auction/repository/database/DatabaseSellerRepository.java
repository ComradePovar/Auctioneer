package edu.core.java.auction.repository.database;

import edu.core.java.auction.AuctionService;
import edu.core.java.auction.repository.SellerRepository;
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
public class DatabaseSellerRepository extends DatabaseRepository<SellerValueObject>
                                      implements SellerRepository{
    private String selectAllQuery = "SELECT * FROM sellers";
    private String selectByIdQuery = "SELECT * FROM sellers WHERE id = ?";
    private String updateByIdQuery = "UPDATE sellers SET name = ?, account_balance = ?, commission_percentage = ? WHERE id = ?";
    private String deleteByIdQuery = "DELETE FROM sellers WHERE id = ?";
    private String insertQuery = "INSERT INTO sellers (id, name, account_balance, commission_percentage) VALUES " +
                                 " (?, ?, ?, ?)";
    private Logger logger = LoggerFactory.getLogger(DatabaseSellerRepository.class);

    @Override
    public boolean contains(Long id) {
        return find(id) != null;
    }

    @Override
    public void add(SellerValueObject object) {
        try{
            Connection connection = AuctionService.getInstance().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setLong(1, object.getId());
            preparedStatement.setString(2, object.getName());
            preparedStatement.setDouble(3, object.getAccountBalance());
            preparedStatement.setDouble(4, object.getCommissionPercentage());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    @Override
    public void update(SellerValueObject object) {
        try{
            Connection connection = AuctionService.getInstance().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateByIdQuery);
            preparedStatement.setString(1, object.getName());
            preparedStatement.setDouble(2, object.getAccountBalance());
            preparedStatement.setDouble(3, object.getCommissionPercentage());
            preparedStatement.setLong(4, object.id);
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    @Override
    public SellerValueObject find(Long id) {
        try{
            Connection connection = AuctionService.getInstance().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectByIdQuery);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            HashMap<Long, SellerValueObject> set = getValues(resultSet);
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
    public Collection<SellerValueObject> getAll() {
        try {
            Connection connection = AuctionService.getInstance().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectAllQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            HashMap<Long, SellerValueObject> set = getValues(resultSet);
            connection.close();
            return set.values();
        } catch (SQLException ex){
            logger.error(ex.getMessage());
        }
        return null;
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
