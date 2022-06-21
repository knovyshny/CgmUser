package de.cgm.test.base.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public interface IBaseCRUDService<T> {
    @NonNull
    List<T> findAll() throws NoSuchFieldException;
    @Nullable
    T findById(@NonNull String id);

    /**
     * insert or update entity in DB
     * spring data implementation of save
     * take care about both insert / update in DB
     * @see <a href="https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongo-template.save-insert">Save/Insert</a>
     * @param entity - entity to save
     * @return - saved entity
     * @author Kiryl Navyshny
     */
    @Nullable
    T save(@NonNull T entity);

    /**
     * remove entity from DB
     * this function intentionally don't throw an exception
     * in the case if entity was not removed ( for example
     * entity not exists in DB ) it should be a task for
     * caller to decide, how to handle it
     * @param id - just an id of the entity
     * @return false - entity was not removed
     *         true - entity was removed
     * @author Kiryl Navyshny
     */
    boolean remove(@NonNull String id);

    @Nullable T replace(@NonNull T entity);

}
