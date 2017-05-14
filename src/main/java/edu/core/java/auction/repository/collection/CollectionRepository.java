package edu.core.java.auction.repository.collection;

import edu.core.java.auction.repository.Repository;
import edu.core.java.auction.vo.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Maxim on 10.05.2017.
 */
public abstract class CollectionRepository<V extends ValueObject> implements Repository<V> {
        private HashMap<Long, V> repository = new HashMap<Long, V>();
        protected Logger logger = LoggerFactory.getLogger(Repository.class);

        public boolean contains(Long id){
            return repository.containsKey(id);
        }

        public void add(V object){
            if (object != null) {
                repository.put(object.id, object);
                incMaxId();
                logger.info("Value object was added.");
            } else {
                logger.warn("Value object is null.");
            }
        }

        public void update(V object){
            if (object != null && repository.containsKey(object.id)){
                repository.put(object.id, object);
                logger.info("Value object was updated.");
            } else {
                logger.warn("Value object is null.");
            }
        }

        public V find(Long id) {
            V object = repository.get(id);
            if (object == null)
                logger.warn("Value object with id = " + id + " was not found.");
            else
                logger.warn("Value object with id = " + id + " was found.");
            return object;
        }

        public void delete(Long id){
            if (repository.remove(id) == null)
                logger.warn("Value object with id = " + id + " was not deleted.");
            else
                logger.info("Value object with id = " + id + " was deleted.");
        }

        public Collection<V> getAll(){
            return repository.values();
        }

        public abstract Long getMaxId();
        protected abstract void incMaxId();
}
