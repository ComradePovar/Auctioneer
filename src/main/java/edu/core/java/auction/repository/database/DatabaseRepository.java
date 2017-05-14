package edu.core.java.auction.repository.database;

import edu.core.java.auction.AuctionService;
import edu.core.java.auction.repository.Repository;
import edu.core.java.auction.vo.ValueObject;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maxim on 10.05.2017.
 */
public abstract class DatabaseRepository<V extends ValueObject> implements Repository<V> {

    protected HashMap<Long, V> select(String query) throws SQLException{
        Connection connection = AuctionService.getInstance().getDataSource().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        HashMap<Long, V> values = getValues(resultSet);
        connection.close();
        return values;
    }

    protected void modify(String query) throws SQLException{
        Connection connection = AuctionService.getInstance().getDataSource().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        connection.close();
    }

    protected abstract HashMap<Long, V> getValues(ResultSet set) throws SQLException;

    protected String getSelectQuery(String table, String condition){
        return "SELECT * FROM " + table + " WHERE " + condition + ";";
    }

    protected String getSelectAllQuery(String table){
        return "SELECT * FROM " + table + ";";
    }

    protected String getInsertQuery(String tableAndColumns, String values){
        return "INSERT INTO " + tableAndColumns + " VALUES (" + values + ")" + ";";
    }

    protected String getDeleteQuery(String table, String condition){
        return "DELETE FROM " + table + " WHERE " + condition + ";";
    }

    protected String getUpdateQuery(String table, String values, String condition){
        return "UPDATE " + table + " SET " + values + " WHERE " + condition + ";";
    }
}
