package com.ftakas.dist.repository;

import com.ftakas.dist.domain.IntegerPropertyVal;

import javax.transaction.Transactional;

@Transactional
public interface IntegerPropertyValRepository extends PropertyValRepository<IntegerPropertyVal> {
}
