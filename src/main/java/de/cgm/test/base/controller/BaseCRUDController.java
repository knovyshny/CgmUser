package de.cgm.test.base.controller;

import de.cgm.test.base.model.BaseOutcomingDto;
import de.cgm.test.base.lang.util.IObjectCopyService;
import de.cgm.test.base.model.BaseEntity;
import de.cgm.test.base.model.BaseIncomingDto;
import de.cgm.test.base.service.IBaseCRUDService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * base controller class, that handle CRUDs for REST API
 *
 * @param <TIncomingDto>
 * @param <TOutcomingDto>
 * @param <TEntity>
 * @author Kiryl Navyshny
 */
public class BaseCRUDController<TIncomingDto extends BaseIncomingDto, TOutcomingDto extends BaseOutcomingDto, TEntity extends BaseEntity> {

    private final Logger logger = LogManager.getLogger(BaseCRUDController.class);

    // intentionally used @Autowired in this place
    // to avoid, that child classes need to know about
    // IObjectCopyService in constructor
    @Autowired
    private
    IObjectCopyService objectCopyService;

    private final IBaseCRUDService<TEntity> service;
    private final Class<TOutcomingDto> outcomingDtoClass;
    private final Class<TEntity> entityClass;

    public BaseCRUDController(IBaseCRUDService<TEntity> service) {
        this.service = service;
        this.outcomingDtoClass = (Class) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
        this.entityClass = (Class) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[2];
    }

    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TOutcomingDto>> findAllIncomingDto() {
        List<TOutcomingDto> result;
        try {
            result = getObjectCopyService().copyFromEntitiesToOutcomingDtos(service.findAll(), outcomingDtoClass);
        } catch (Exception eError) {
            logger.error("Cannot execute findAllIncomingDto", eError);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(result);
    }

    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TOutcomingDto> findIncomingDtoById(@NonNull @PathVariable final String Id) {
        TOutcomingDto result = null;
        try {
            var foundEntity = service.findById(Id);
            if (foundEntity != null) {
                result = getObjectCopyService().copyFromEntityToOutcomingDto(foundEntity, outcomingDtoClass);
            }
        } catch (Exception eError) {
            logger.error("Cannot execute findIncomingDtoById", eError);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result != null ? ResponseEntity.ok(result) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> delete(@NonNull @PathVariable final String Id) {
        try {
            if (!service.remove(Id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception eError) {
            logger.error("Cannot execute delete", eError);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TOutcomingDto> addIncomingDto(@Valid @RequestBody TIncomingDto incomingDto) {
        if (incomingDto.getId() == null) {
            incomingDto.setId(UUID.randomUUID().toString());
        }
        TOutcomingDto result = null;
        try {
            // check if resource id already exists and return BAD_REUEST in this case
            if (service.findById(incomingDto.getId()) != null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            var savedEntity = service.save(getObjectCopyService().copyFromIncomingDtoToEntity(incomingDto, entityClass));
            if (savedEntity != null) {
                result = getObjectCopyService().copyFromEntityToOutcomingDto(savedEntity, outcomingDtoClass);
            }
        } catch (Exception eError) {
            logger.error("Cannot execute addIncomingDto", eError);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result != null ? ResponseEntity.ok(result) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TOutcomingDto> updateIncomingDto(@PathVariable final String Id, @Valid @RequestBody TIncomingDto incomingDto) {

        // in the case if Id from request don't suits to Id from IncomingDto return BAD_REQUEST
        if (!Objects.equals(Id, incomingDto.getId()) && incomingDto.getId() != null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        incomingDto.setId(Id);
        TOutcomingDto result = null;
        try {
            var savedEntity = service.replace(getObjectCopyService().copyFromIncomingDtoToEntity(incomingDto, entityClass));
            if (savedEntity != null) {
                result = getObjectCopyService().copyFromEntityToOutcomingDto(savedEntity, outcomingDtoClass);
            }
        } catch (Exception eError) {
            logger.error("Cannot execute updateIncomingDto", eError);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result != null ? ResponseEntity.ok(result) : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    public IObjectCopyService getObjectCopyService() {
        return objectCopyService;
    }
}
