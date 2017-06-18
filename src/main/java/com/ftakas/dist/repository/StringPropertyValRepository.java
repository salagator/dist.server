package com.ftakas.dist.repository;

import com.ftakas.dist.domain.property.StringPropertyVal;

import javax.transaction.Transactional;

@Transactional
public interface StringPropertyValRepository extends PropertyValRepository<StringPropertyVal> {
}
