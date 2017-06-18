package com.ftakas.dist.repository;

import com.ftakas.dist.domain.property.FloatingPointPropertyVal;

import javax.transaction.Transactional;

@Transactional
public interface FloatingPointPropertyValRepository extends PropertyValRepository<FloatingPointPropertyVal> {
}
