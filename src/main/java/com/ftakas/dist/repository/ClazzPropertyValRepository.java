package com.ftakas.dist.repository;

import com.ftakas.dist.domain.property.ClazzPropertyVal;

import javax.transaction.Transactional;

@Transactional
public interface ClazzPropertyValRepository extends PropertyValRepository<ClazzPropertyVal> {
}
