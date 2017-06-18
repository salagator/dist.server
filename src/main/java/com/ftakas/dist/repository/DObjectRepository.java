package com.ftakas.dist.repository;

import com.ftakas.dist.domain.DObject;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface DObjectRepository extends CrudRepository<DObject, Long> {
}
