package com.ftakas.dist.repository;

import com.ftakas.dist.domain.Clazz;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface ClazzRepository extends CrudRepository<Clazz, Long> {
}
