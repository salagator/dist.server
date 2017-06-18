package com.ftakas.dist.repository;

import com.ftakas.dist.domain.property.IntegerPropertyVal;

import javax.transaction.Transactional;

@Transactional
public interface IntegerPropertyValRepository extends PropertyValRepository<IntegerPropertyVal> {
}
