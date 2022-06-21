package de.cgm.test.base.lang.util;

import de.cgm.test.base.model.BaseOutcomingDto;
import de.cgm.test.base.model.BaseEntity;
import de.cgm.test.base.model.BaseIncomingDto;
import org.springframework.lang.NonNull;

import java.util.List;

public interface IObjectCopyService {
    <TIncomingDto extends BaseIncomingDto, TEntity extends BaseEntity> TEntity copyFromIncomingDtoToEntity(@NonNull TIncomingDto incomingDto, Class<TEntity> entityClass);

    <TEntity extends BaseEntity, TOutcomingDto extends BaseOutcomingDto> TOutcomingDto copyFromEntityToOutcomingDto(@NonNull TEntity entity, Class<TOutcomingDto> outcomingDtoClass);

    <TEntity extends BaseEntity, TOutcomingDto extends BaseOutcomingDto> List<TOutcomingDto> copyFromEntitiesToOutcomingDtos(@NonNull List<TEntity> entities, Class<TOutcomingDto> outcomingDtoClass);
}
