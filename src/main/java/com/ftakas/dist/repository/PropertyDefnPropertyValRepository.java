package com.ftakas.dist.repository;

import com.ftakas.dist.domain.property.PropertyDefnPropertyVal;

import javax.transaction.Transactional;

@Transactional
public interface PropertyDefnPropertyValRepository extends PropertyValRepository<PropertyDefnPropertyVal> {
}
