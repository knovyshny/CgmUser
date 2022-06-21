package de.cgm.test.base.service;

import de.cgm.test.base.model.BaseEntity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

public class BaseCRUDService<T extends BaseEntity> implements IBaseCRUDService<T> {
    private final Class<T> persistentClass;
    private final MongoTemplate mongoTemplate;

    public BaseCRUDService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        this.persistentClass = (Class)((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public @NonNull List<T> findAll() {
        return getMongoTemplate().query(persistentClass).stream().distinct().toList();
    }

    @Override
    public @Nullable T findById(@NonNull String id) {
        return getMongoTemplate().findOne(query(where("id").is(id)), persistentClass);
    }

    @Override
    public @NonNull T save(@NonNull T entity) {
        return getMongoTemplate().save(entity);
    }

    public @Nullable T replace(@NonNull T entity) {
        // findAndReplace returns previous state of entity
        if ( getMongoTemplate().findAndReplace(query(where("id").is(entity.getId())), entity) != null ){
            return entity;
        }

        return null;
    }

    @Override
    public boolean remove(@NonNull String id) {
        return getMongoTemplate().remove(query(where("id").is(id)), persistentClass).getDeletedCount() == 1;
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }
}
