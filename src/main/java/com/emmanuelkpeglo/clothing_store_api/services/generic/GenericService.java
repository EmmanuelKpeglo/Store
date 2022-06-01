package com.emmanuelkpeglo.clothing_store_api.services.generic;

import com.emmanuelkpeglo.clothing_store_api.models.base.BaseEntity;

import java.util.List;

public interface GenericService<T extends BaseEntity> {
    T save(T entity);
    List<T> findAll();
    T findById(Long id);
    void delete(Long id);
    T update(Long id, T entity);
}
