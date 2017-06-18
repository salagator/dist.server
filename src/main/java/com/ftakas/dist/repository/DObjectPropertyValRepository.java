package com.ftakas.dist.repository;

import com.ftakas.dist.domain.property.DObjectPropertyVal;

import javax.transaction.Transactional;

@Transactional
public interface DObjectPropertyValRepository extends PropertyValRepository<DObjectPropertyVal> {
}
