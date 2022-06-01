package com.emmanuelkpeglo.clothing_store_api.controllers.generic.implementations;

import com.emmanuelkpeglo.clothing_store_api.controllers.generic.GenericController;
import com.emmanuelkpeglo.clothing_store_api.models.base.BaseEntity;
import com.emmanuelkpeglo.clothing_store_api.services.generic.GenericService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@ResponseBody
public class GenericControllerImpl<T, K extends BaseEntity> implements GenericController<T, K> {
    private final Class<T> dtoType;
    private final Class<K> entityType;

    @Autowired
    private GenericService<K> service;

    @Autowired
    private ModelMapper modelMapper;

    public GenericControllerImpl(Class<T> dtoType, Class<K> entityType) {
        this.dtoType = dtoType;
        this.entityType = entityType;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<T>> getAll() {
        List<T> entityDtos = service.findAll()
                .stream()
                .map(entity -> modelMapper.map(entity, dtoType))
                .collect(Collectors.toList());

        return new ResponseEntity<>(entityDtos, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable Long id) {
        K entity = service.findById(id);
        T entityDto = modelMapper.map(entity, dtoType);

        return new ResponseEntity<>(entityDto, HttpStatus.OK);
    }

    @Override
    @PostMapping("/add")
    public ResponseEntity<T> save(@RequestBody T entityRequest) {
        K entity = modelMapper.map(entityRequest, entityType);
        K savedEntity = service.save(entity);
        T savedEntityDto = modelMapper.map(savedEntity, dtoType);

        return new ResponseEntity<>(savedEntityDto, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable Long id, @RequestBody T entityRequest) {
        K entity = modelMapper.map(entityRequest, entityType);
        K updatedEntity = service.update(id, entity);
        T updatedEntityDto = modelMapper.map(updatedEntity, dtoType);

        return new ResponseEntity<>(updatedEntityDto, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);

        return new ResponseEntity<>("Resource with id: " + id + " removed!", HttpStatus.OK);
    }
}
