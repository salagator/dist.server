package com.ftakas.dist.repository;

import com.ftakas.dist.domain.BooleanPropertyVal;

import javax.transaction.Transactional;

@Transactional
public interface BooleanPropertyValRepository extends PropertyValRepository<BooleanPropertyVal> {
}
