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
    protected abstract HashMap<Long, V> getValues(ResultSet set) throws SQLException;
}
