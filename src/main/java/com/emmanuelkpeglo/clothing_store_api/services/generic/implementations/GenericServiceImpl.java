package com.emmanuelkpeglo.clothing_store_api.services.generic.implementations;

import com.emmanuelkpeglo.clothing_store_api.dao.generic.GenericRepository;
import com.emmanuelkpeglo.clothing_store_api.exceptions.ResourceNotFoundException;
import com.emmanuelkpeglo.clothing_store_api.models.base.BaseEntity;
import com.emmanuelkpeglo.clothing_store_api.services.generic.GenericService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class GenericServiceImpl<T extends BaseEntity> implements GenericService<T> {

    @Autowired
    protected GenericRepository<T> repository;

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource with id: " + id + " not found!"));
    }

    @Override
    public void delete(Long id) {
        T entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource with id: " + id + " not found!"));

        repository.delete(entity);
    }

    @Override
    public abstract T update(Long id, T entity);
}
