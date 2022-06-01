package com.emmanuelkpeglo.clothing_store_api.controllers.generic;

//import com.emmanuelkpeglo.clothing_store_api.dtos.base.BaseEntityDto;
import com.emmanuelkpeglo.clothing_store_api.dtos.base.BaseEntityDto;
import com.emmanuelkpeglo.clothing_store_api.models.base.BaseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface GenericController<T, K extends BaseEntity> {
    ResponseEntity<List<T>> getAll();
    ResponseEntity<T> getById(@PathVariable Long id);
    ResponseEntity<T> save(@RequestBody T entityRequest);
    ResponseEntity<T> update(@PathVariable Long id, @RequestBody T entityRequest);
    ResponseEntity<String> delete(@PathVariable Long id);
}
