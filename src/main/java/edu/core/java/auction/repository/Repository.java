package edu.core.java.auction.repository;

import edu.core.java.auction.vo.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Max on 06.03.2017.
 */
public interface Repository<V extends ValueObject> {

    boolean contains(Long id);

    void add(V object);

    void update(V object);

    V find(Long id);

    void delete(Long id);

    Collection<V> getAll();

    Long getMaxId();
    void incMaxId();
}
