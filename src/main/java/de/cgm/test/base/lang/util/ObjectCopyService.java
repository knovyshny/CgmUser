package de.cgm.test.base.lang.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.cgm.test.base.model.BaseOutcomingDto;
import de.cgm.test.base.model.BaseEntity;
import de.cgm.test.base.model.BaseIncomingDto;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

/**
 * service to take care of copy attributes
 * from entities and incoming / outgoing dtos
 * Gson lib is work fine for most cases of Pojos,
 * in the case of complex structures
 * you'll probably need to write own
 * serializer / deserializer for Gson
 * @author Kiryl Navyshny
 */
@Service
public final class ObjectCopyService implements IObjectCopyService {
    private final Gson gson;
    public ObjectCopyService(){
        gson = new Gson();
    }
    @Override
    @NonNull
    public <TIncomingDto extends BaseIncomingDto, TEntity extends BaseEntity> TEntity copyFromIncomingDtoToEntity(@NonNull TIncomingDto incomingDto, Class<TEntity> entityClass){
        return gson.fromJson(gson.toJson(incomingDto), entityClass);
    }

    @Override
    @NonNull
    public <TEntity extends BaseEntity, TOutcomingDto extends BaseOutcomingDto> TOutcomingDto copyFromEntityToOutcomingDto(@NonNull TEntity entity, Class<TOutcomingDto> outcomingDtoClass){
        return gson.fromJson(gson.toJson(entity), outcomingDtoClass);
    }

    @Override
    @NonNull
    public <TEntity extends BaseEntity, TOutcomingDto extends BaseOutcomingDto> List<TOutcomingDto> copyFromEntitiesToOutcomingDtos(@NonNull List<TEntity> entities, Class<TOutcomingDto> outcomingDtoClass){
        final Type outcomingListType = new TypeToken<List<TOutcomingDto>>(){}.getType();
        return gson.fromJson(gson.toJson(entities), outcomingListType);
    }
}
