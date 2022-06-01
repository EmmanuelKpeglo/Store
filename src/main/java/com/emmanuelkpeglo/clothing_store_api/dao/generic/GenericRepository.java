package com.emmanuelkpeglo.clothing_store_api.dao.generic;

import com.emmanuelkpeglo.clothing_store_api.models.base.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenericRepository<T extends BaseEntity> extends JpaRepository<T, Long> {
}
